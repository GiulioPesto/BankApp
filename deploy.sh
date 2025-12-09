#!/bin/bash

# Script di deployment per BankApp
# Uso: ./deploy.sh [staging|production]

set -e

ENVIRONMENT=${1:-staging}
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "🚀 Starting deployment to $ENVIRONMENT..."

# Colori per output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Funzione per logging
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Verifica ambiente
if [[ "$ENVIRONMENT" != "staging" && "$ENVIRONMENT" != "production" ]]; then
    log_error "Environment must be 'staging' or 'production'"
    exit 1
fi

# Carica variabili d'ambiente
ENV_FILE="$SCRIPT_DIR/.env.$ENVIRONMENT"
if [[ ! -f "$ENV_FILE" ]]; then
    log_error "Environment file not found: $ENV_FILE"
    log_info "Create it from .env.example: cp .env.example .env.$ENVIRONMENT"
    exit 1
fi

log_info "Loading environment variables from $ENV_FILE"
export $(cat "$ENV_FILE" | grep -v '^#' | xargs)

# Verifica che Docker sia installato
if ! command -v docker &> /dev/null; then
    log_error "Docker is not installed"
    exit 1
fi

# Verifica che docker-compose sia installato
if ! command -v docker-compose &> /dev/null; then
    log_error "docker-compose is not installed"
    exit 1
fi

# Pull dell'immagine più recente
log_info "Pulling latest Docker image..."
docker pull "${DOCKER_USERNAME}/bankapp:${IMAGE_TAG}"

# Backup del database (opzionale)
if [[ "$ENVIRONMENT" == "production" ]]; then
    log_warn "Production deployment - consider backing up the database first"
    read -p "Continue? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        log_error "Deployment cancelled"
        exit 1
    fi
fi

# Stop dei container esistenti
log_info "Stopping existing containers..."
docker-compose -f "$SCRIPT_DIR/docker-compose.yml" --env-file "$ENV_FILE" down

# Start dei nuovi container
log_info "Starting new containers..."
docker-compose -f "$SCRIPT_DIR/docker-compose.yml" --env-file "$ENV_FILE" up -d

# Attendi che l'applicazione sia healthy
log_info "Waiting for application to be healthy..."
RETRY_COUNT=0
MAX_RETRIES=30

while [[ $RETRY_COUNT -lt $MAX_RETRIES ]]; do
    if docker-compose -f "$SCRIPT_DIR/docker-compose.yml" --env-file "$ENV_FILE" ps | grep -q "healthy"; then
        log_info "✅ Application is healthy!"
        break
    fi

    RETRY_COUNT=$((RETRY_COUNT + 1))
    echo -n "."
    sleep 2
done

if [[ $RETRY_COUNT -eq $MAX_RETRIES ]]; then
    log_error "Application failed to start within expected time"
    log_info "Check logs with: docker-compose logs bankapp"
    exit 1
fi

# Mostra i container in esecuzione
log_info "Running containers:"
docker-compose -f "$SCRIPT_DIR/docker-compose.yml" --env-file "$ENV_FILE" ps

# Cleanup delle immagini vecchie
log_info "Cleaning up old images..."
docker image prune -f

log_info "🎉 Deployment to $ENVIRONMENT completed successfully!"
log_info "Application URL: http://localhost:${APP_PORT:-8080}"
log_info "View logs: docker-compose logs -f bankapp"


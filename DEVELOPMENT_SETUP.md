# Configurazione Ambiente di Development

## Overview
Questo documento descrive la configurazione dell'ambiente di sviluppo (development) per BankApp.

## Branch e Workflow

### Branch: `develop`
- **Profilo Spring**: `dev`
- **Schema Database**: `bank_schema_dev`
- **Workflow Build**: `.github/workflows/build-dev.yml`
- **Workflow Deploy**: `.github/workflows/deploy-dev.yml`
- **Docker Tag**: `dev-latest`, `dev-{sha}`

## Configurazione Database

### Schema
Lo schema utilizzato in ambiente development è: `bank_schema_dev`

### Liquibase Context
Il context Liquibase per l'ambiente dev è: `dev`

Tutti i changeset verranno eseguiti con questo context, permettendo di:
- Creare lo schema `bank_schema_dev`
- Eseguire tutte le migrazioni nel contesto dev
- Mantenere separati i dati di dev da local e prod

## Deploy

### Automatico
Quando si fa push sul branch `develop`:
1. Si avvia automaticamente il workflow `build-dev.yml`
2. Se il build e i test hanno successo, si avvia `deploy-dev.yml`
3. Viene creata un'immagine Docker con tag `dev-latest`
4. L'immagine viene deployata sull'ambiente di sviluppo

### Manuale
È possibile avviare manualmente il deploy da GitHub Actions:
1. Vai su Actions > Deploy to Development
2. Clicca su "Run workflow"
3. Seleziona il branch `develop`
4. Clicca su "Run workflow"

## Configurazione Locale

### Variabili d'Ambiente
Copia il file `.env.dev.example` in `.env` e modifica i valori:

```bash
cp .env.dev.example .env
```

Modifica le variabili nel file `.env`:
- `DATABASE_URL_DEV`: URL del database PostgreSQL/Supabase
- `DATABASE_USERNAME_DEV`: Username del database
- `DATABASE_PASSWORD_DEV`: Password del database
- Altri parametri di configurazione

### Esecuzione con Docker Compose

```bash
# Build e start
docker-compose -f docker-compose-dev.yml up -d

# Visualizza i logs
docker-compose -f docker-compose-dev.yml logs -f

# Stop
docker-compose -f docker-compose-dev.yml down
```

### Esecuzione Locale (senza Docker)

```bash
# Con Maven
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Con Java (dopo il build)
java -jar -Dspring.profiles.active=dev target/BankApp-0.0.1-SNAPSHOT.jar
```

## GitHub Secrets Necessari

Per il corretto funzionamento del deploy automatico, configura questi secrets in GitHub:

- `DOCKER_USERNAME`: Username di Docker Hub
- `DOCKER_PASSWORD`: Password o Access Token di Docker Hub
- `RENDER_DEPLOY_HOOK_DEV`: Webhook URL per il deploy su Render (ambiente dev)

### Come Configurare i Secrets
1. Vai su GitHub Repository > Settings > Secrets and variables > Actions
2. Clicca su "New repository secret"
3. Aggiungi ciascun secret con il nome e valore corrispondente

## Differenze tra Ambienti

| Aspetto | Local | Development | Production |
|---------|-------|-------------|------------|
| Branch | qualsiasi | develop | prod |
| Profilo | local | dev | prod |
| Schema DB | bank_schema_local | bank_schema_dev | bank_schema_prod |
| Context Liquibase | local | dev | prod |
| Docker Tag | - | dev-latest | latest |
| Show SQL | true | true | true |
| Log Level | DEBUG | DEBUG | INFO |
| Health Details | always | always | never |

## Troubleshooting

### Lo schema non viene creato
Verifica che:
1. Liquibase sia abilitato: `LIQUIBASE_ENABLED=true`
2. Il context sia corretto: `dev`
3. Le credenziali del database abbiano i permessi per creare schemi

### Il deploy fallisce
Controlla:
1. I secrets GitHub siano configurati correttamente
2. Il workflow build-dev.yml sia completato con successo
3. I logs del workflow deploy-dev.yml per vedere l'errore specifico

### Problemi di connessione al database
Verifica:
1. L'URL del database sia corretto
2. Le credenziali siano valide
3. Il database sia raggiungibile dalla tua posizione
4. Il firewall/security group permetta la connessione

## Prossimi Passi

Dopo aver configurato l'ambiente dev:
1. Crea un branch da `develop` per le tue feature
2. Fai commit e push sul tuo feature branch
3. Crea una Pull Request verso `develop`
4. Dopo il merge su `develop`, il deploy automatico si attiverà


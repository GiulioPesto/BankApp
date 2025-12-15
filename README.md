# BankApp

Applicazione bancaria sviluppata con Spring Boot e PostgreSQL (Supabase).

## 📋 Prerequisiti

- Java 21 o superiore
- Maven 3.8+
- Account Supabase (per il database)
- Docker (opzionale, per deployment)

## 📚 Documentazione degli Ambienti

- **[Setup Ambiente Development](DEVELOPMENT_SETUP.md)**: Configurazione per l'ambiente di sviluppo (branch `develop`)
- **[Schema Configuration](SCHEMA_CONFIGURATION.md)**: Dettagli sulla configurazione degli schemi database
- **[Troubleshooting](TROUBLESHOOTING.md)**: Risoluzione problemi comuni

## 🚀 Setup Locale

### 1. Configurazione Database

Crea un file `.env` nella root del progetto copiando il template:

```bash
cp .env.example .env
```

Modifica il file `.env` con le tue credenziali Supabase:

```bash
# Per Supabase Connection Pooling (consigliato)
SPRING_DATASOURCE_URL=jdbc:postgresql://db.YOUR_PROJECT_ID.supabase.co:6543/postgres?sslmode=require&prepareThreshold=0
SPRING_DATASOURCE_USERNAME=postgres.YOUR_PROJECT_ID
SPRING_DATASOURCE_PASSWORD=your_password_here
```

> **Nota**: Supabase offre due tipi di connessione:
> - **Connection Pooling (porta 6543)**: consigliato per applicazioni (usa questo)
> - **Direct Connection (porta 5432)**: solo per tool amministrativi

### 2. Avvio Applicazione

**Opzione A: Con script (consigliato)**
```bash
chmod +x run-local.sh
./run-local.sh
```

**Opzione B: Con Maven manualmente**
```bash
export $(cat .env | grep -v '^#' | xargs)
./mvnw spring-boot:run
```

**Opzione C: Da IntelliJ IDEA**
1. Apri Run/Debug Configurations
2. Aggiungi le variabili d'ambiente dal file `.env`
3. Oppure usa il plugin EnvFile per caricare automaticamente `.env`

### 3. Verifica

L'applicazione sarà disponibile su: `http://localhost:8080`

Health check: `http://localhost:8080/actuator/health`

## 🐳 Docker

### Build immagine
```bash
docker build -t bankapp:latest .
```

### Esegui con Docker
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="your_url" \
  -e SPRING_DATASOURCE_USERNAME="your_username" \
  -e SPRING_DATASOURCE_PASSWORD="your_password" \
  bankapp:latest
```

### Docker Compose
```bash
docker-compose up -d
```

## 📦 Build & Deploy

### Build per Produzione
```bash
./mvnw clean package -DskipTests
```

### CI/CD Pipeline

Il progetto include una pipeline GitHub Actions che:
- 🧪 Esegue i test automaticamente su ogni push
- 🐳 Builda e pusha immagini Docker su Docker Hub
- 🚀 Deploya automaticamente su:
  - **Staging**: branch `develop`
  - **Production**: branch `prod` (con tag `latest`)

#### Branch Strategy
- `develop` → staging environment
- `main` → branch stabile (no auto-deploy)
- `prod` → production environment + tag Docker `latest`

## 🔐 Sicurezza

⚠️ **IMPORTANTE**:
- Non committare mai il file `.env` (già nel `.gitignore`)
- Usa sempre variabili d'ambiente per credenziali sensibili
- In produzione, configura i secrets su GitHub Actions o nel tuo provider cloud

## 📊 Database Migrations

Le migrazioni vengono gestite con Liquibase:
- Changelog: `src/main/resources/db/changelog/db.changelog-master.xml`
- Schema: `bank_schema`

## 🛠️ Tecnologie

- **Backend**: Spring Boot 3.5.3
- **Database**: PostgreSQL (Supabase)
- **Migrations**: Liquibase
- **Build**: Maven
- **CI/CD**: GitHub Actions
- **Container**: Docker

## 📝 Endpoints

### Actuator
- Health: `/actuator/health`
- Info: `/actuator/info`
- Metrics: `/actuator/metrics`
- Prometheus: `/actuator/prometheus`

### API
TODO: Documentare gli endpoint dell'applicazione

## 🧪 Testing

```bash
./mvnw test
```

## 📖 Documentazione

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Supabase Documentation](https://supabase.com/docs)
- [Liquibase Documentation](https://docs.liquibase.com/)

## 👨‍💻 Autore

Giulio Pastore


# Multi-stage build per ottimizzare l'immagine Docker

# Stage 1: Build (opzionale, se non usi gli artifacts da CI)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crea un utente non-root per sicurezza
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia il JAR dall'artifact (se usi CI) o dal build stage
COPY --from=builder /app/target/*.jar app.jar

# Espone la porta dell'applicazione
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Variabili d'ambiente di default (possono essere sovrascritte)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Esegue l'applicazione
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]


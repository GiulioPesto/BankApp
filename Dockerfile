# Usa un'immagine base JRE leggera e sicura
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crea un utente non-root per sicurezza
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia il JAR già compilato dalla pipeline CI nella cartella target
COPY target/*.jar app.jar

# Espone la porta dell'applicazione
EXPOSE 8080

# Health check per monitorare lo stato dell'applicazione
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Variabili d'ambiente di default (possono essere sovrascritte)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=prod

# Esegue l'applicazione
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]


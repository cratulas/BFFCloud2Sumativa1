# Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -q -DskipTests clean package

# Run
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
# Perfil docker; puedes añadir JAVA_OPTS si quieres memory flags
ENTRYPOINT ["sh","-c","java -Dspring.profiles.active=docker $JAVA_OPTS -jar app.jar"]

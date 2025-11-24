FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY api-controle-financeiro/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY api-controle-financeiro/src ./src
RUN mvn -f pom.xml clean package -DskipTests

FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

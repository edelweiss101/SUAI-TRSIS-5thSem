FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/lab8_1.jar app_1.jar
COPY target/keystore.p12 /app/target/keystore.p12
ENTRYPOINT ["java", "-jar", "app_1.jar"]
EXPOSE 8081
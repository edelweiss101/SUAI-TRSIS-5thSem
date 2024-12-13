FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/lab8_2.jar app_2.jar
COPY target/keystore.p12 /app/target/keystore.p12
ENTRYPOINT ["java", "-jar", "app_2.jar"]
EXPOSE 8082
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/wallet-*.jar app.jar
COPY config/ /app/config/
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/config/"]
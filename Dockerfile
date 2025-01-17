FROM openjdk:17-jdk-slim

# Define o diretório de trabalho no container
WORKDIR /app

# Copia o arquivo JAR da aplicação
COPY application/target/url-shortener-0.0.1-SNAPSHOT.jar url-shortener.jar

# Expor a porta que a aplicação usará
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "url-shortener.jar"]
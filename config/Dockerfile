FROM adorsys/openjdk-jre-base:8

WORKDIR /opt/config-service

COPY target/*.jar config-service.jar

CMD ["java", "-jar", "config-service.jar"]
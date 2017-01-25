FROM adorsys/openjdk-jre-base:8

WORKDIR /opt/gateway

COPY target/*.jar gateway.jar

CMD ["java", "-jar", "gateway.jar"]
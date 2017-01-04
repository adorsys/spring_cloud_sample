FROM adorsys/openjdk-jre-base:8

WORKDIR /opt/product-service

COPY target/*.jar product-service.jar

CMD ["java", "-jar", "product-service.jar"]
FROM adorsys/openjdk-jre-base:8

WORKDIR /opt/cart-service

COPY target/*.jar cart-service.jar

CMD ["java", "-jar", "cart-service.jar"]
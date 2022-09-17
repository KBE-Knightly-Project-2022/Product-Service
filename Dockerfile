FROM maven:3.8.4-openjdk-17

WORKDIR /spring_warehouse
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
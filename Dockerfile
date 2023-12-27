FROM eclipse-temurin:17.0.9_9-jre-jammy

RUN mkdir -p /home/app
COPY ./build/libs/*.jar /home/app

WORKDIR /home/app

CMD ["java", "-jar", "spring-boot-training-0.0.1-SNAPSHOT.jar"]
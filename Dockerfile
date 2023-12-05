FROM openjdk:17-ea-slim
VOLUME /tmp
COPY build/libs/sideproject-spring-1.0.jar dnchEduService.jar
ENTRYPOINT ["java", "-jar", "dnchEduService.jar"]
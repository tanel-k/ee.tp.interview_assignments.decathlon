FROM gradle:6.4-jdk8 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build

FROM adoptopenjdk/openjdk8:alpine-slim

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -Dfile.encoding=UTF-8"

COPY --from=builder /home/gradle/src/build/libs/decathlon_service-0.0.1.jar /opt/

EXPOSE 8081

WORKDIR /opt
CMD ["java", "-jar", "decathlon_service-0.0.1.jar"]

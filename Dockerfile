FROM openjdk:8-jdk-alpine as builder

RUN mkdir -p /gradle

ADD  build.gradle /gradle
ADD  settings.gradle /gradle
ADD  src /gradle/src
ADD  gradlew /gradle
ADD  gradle /gradle/gradle
WORKDIR /gradle
RUN ./gradlew build

FROM adoptopenjdk/openjdk8-openj9:x86_64-alpine-jdk8u181-b13_openj9-0.9.0-slim

ENV CASSANDRA_HOST localhost
ENV CASSANDRA_PORT 9042
ENV CASSANDRA_KEYSPACE user_service
ENV REDIS_PORT 6379
ENV REDIS_HOST localhost
ENV EUREKA_URL localhost:8761
ENV SERVER_PORT 8092
ENV USER_SERVICE_PREFIX users
ENV FACEBOOK_CLIENT_ID clientId
ENV FACEBOOK_CLIENT_SECRET clientSecret
ENV GOOGLE_CLIENT_ID clientId
ENV GOOGLE_CLIENT_SECRET clientSecret
ENV SRING_MAIL_USERNAME mailserver
ENV SPRING_MAIL_PASSWORD password
ENV FONTEND_URL http://localhost:3000
ENV FONTEND_MAIN dashboard
ENV FONTEND_LOGIN login
ENV LOGGING_LEVEL INFO
ENV XMX 128m

RUN mkdir -p /app
COPY --from=builder /gradle/build/libs/user-service.jar /app/
COPY run.sh /app/
WORKDIR /app

ENTRYPOINT [ "sh", "./run.sh"]

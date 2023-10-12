# syntax=docker/dockerfile:1
# Downloads the new relic agent to use it in the target image
FROM alpine:latest AS unzipper
RUN apk add unzip wget curl unzip; curl -O https://download.newrelic.com/newrelic/java-agent/newrelic-agent/current/newrelic-java.zip ; unzip newrelic-java.zip

FROM eclipse-temurin:21-alpine

# Prepare no local application jar
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app.jar

# Set up monitoring
COPY  --from=unzipper /newrelic/newrelic.jar /newrelic.jar
ADD ./newrelic.yml /newrelic.yml
ENV NEWRELIC_KEY=""
ENV JAVA_OPTS="$JAVA_OPTS -javaagent:/newrelic.jar "

ENTRYPOINT java ${JAVA_OPTS} -Dnewrelic.config.license_key=$NEWRELIC_KEY -jar /app.jar
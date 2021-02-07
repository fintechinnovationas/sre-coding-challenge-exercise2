FROM azul/zulu-openjdk-alpine:13

COPY /target/sre-coding-challenge.jar /app.jar

# Add app user and get su-exec for easy step-down from root
RUN addgroup -S --gid 1000 neonomics && \
    adduser -S --uid 1000 --shell=/bin/ash  -G neonomics neonomics

RUN chown -R neonomics:neonomics /app.jar

# Disable root login
RUN sed -i -e 's/\/root\:\/bin\/ash/\/root\:\/sbin\/nologin/g' /etc/passwd

USER neonomics

EXPOSE 8888

ENTRYPOINT ["java","-jar","/app.jar"]

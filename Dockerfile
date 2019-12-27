FROM gradle:5.4.1-alpine as builder

USER root

ENV APP_DIR /app
WORKDIR $APP_DIR

COPY build.gradle.kts $APP_DIR/
COPY settings.gradle.kts $APP_DIR/

RUN gradle dependencies

COPY . $APP_DIR

RUN gradle build

USER guest

# -----------------------------------------------------------------------------

FROM openjdk:13-slim-buster as service

WORKDIR /app

COPY --from=builder /app/init.sh /app
COPY --from=builder /app/build/libs/risk-insurance-*.jar /app/

EXPOSE 8080

ENTRYPOINT ["sh", "init.sh"]
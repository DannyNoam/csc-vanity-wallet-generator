FROM openjdk:11

MAINTAINER Danny Noam <goodluck@gettingmyemail.com>

RUN mkdir ./app && mkdir ./scripts

COPY ./docker/scripts/entrypoint.sh ./scripts

COPY ./target/wallet-service-*.jar /app

RUN ["chmod", "+x", "../scripts/entrypoint.sh"]

WORKDIR /app

ENTRYPOINT ["../scripts/entrypoint.sh"]




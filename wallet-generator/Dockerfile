FROM mhart/alpine-node:10.5.0

RUN mkdir ~/app && mkdir ~/app/src

COPY ./src /app/src

COPY ./.babelrc /app

COPY ./package.json /app

COPY ./scripts/entrypoint.sh ./app/entrypoint.sh

RUN ls -la /app/src

RUN ls -la /app

RUN cd /app && npm install

RUN ["chmod", "+x", "/app/entrypoint.sh"]

ENTRYPOINT /app/entrypoint.sh
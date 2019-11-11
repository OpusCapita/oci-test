FROM node:12-alpine

ARG CREATED
ARG SOURCE
ARG REVISION

LABEL maintainer="egor.stambakio@opuscapita.com" \
      org.opencontainers.image.created="$CREATED" \
      org.opencontainers.image.source="$SOURCE" \
      org.opencontainers.image.revision="$REVISION" \
      org.opencontainers.image.vendor="OpusCapita"

WORKDIR /app

COPY / ./
RUN npm install --production

RUN rm -rf .git* build .circleci

ENV HOST 0.0.0.0
ENV PORT 3333

EXPOSE ${PORT}

CMD ["npm","start"]

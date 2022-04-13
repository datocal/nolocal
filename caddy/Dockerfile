FROM caddy:builder AS builder

RUN xcaddy build --with github.com/CarsonHoffman/caddy-discord-interactions-verifier

FROM caddy:latest

COPY --from=builder /usr/bin/caddy /usr/bin/caddy
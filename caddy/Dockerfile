FROM caddy:2.6-builder AS builder

RUN xcaddy build --with github.com/CarsonHoffman/caddy-discord-interactions-verifier

FROM caddy:2.6

COPY --from=builder /usr/bin/caddy /usr/bin/caddy

COPY Caddyfile /etc/caddy/
---
sidebar_position: 3
---

# Security

In order to be validated by discord, the implemented interactions endpoint must validate that the invocation is coming from discord.

The full details about how discord send messages and how to validate them can be found [in their official documentation](https://discord.com/developers/docs/interactions/receiving-and-responding#security-and-authorization)

To implement this, a caddy service is set up using the [CarsonHoffman mod for caddy](https://github.com/CarsonHoffman/caddy-discord-interactions-verifier).

The Caddy reverse proxy will check the calls to the [interactions endpoint](./adding#how-the-magic-works) veryfing 
the headers to ensure the call is made from discord.

The modified caddy has been built and uploaded [to the docker hub](https://hub.docker.com/r/davidtca/caddy-discord)
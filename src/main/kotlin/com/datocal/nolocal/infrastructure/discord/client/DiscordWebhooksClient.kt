package com.datocal.nolocal.infrastructure.discord.client

import org.springframework.web.reactive.function.client.WebClient

class DiscordWebhooksClient(
    private val client: WebClient,
)

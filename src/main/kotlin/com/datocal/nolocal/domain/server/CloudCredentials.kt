package com.datocal.nolocal.domain.server

import com.datocal.nolocal.domain.account.Account

data class CloudCredentials(
    val owner: Account,
    val flavor: CloudFlavor,
    val encryptedToken: String,
)

package com.datocal.nolocal.domain.server

import com.datocal.nolocal.domain.account.Account

interface CloudCredentialsRepository {
    fun save(cloudCredentials: CloudCredentials)
    fun get(owner: Account, flavor: CloudFlavor): CloudCredentials
}

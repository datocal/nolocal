package com.datocal.nolocal.application.server

import com.datocal.nolocal.domain.account.Account
import com.datocal.nolocal.domain.account.Tenant
import com.datocal.nolocal.domain.server.CloudCredentials
import com.datocal.nolocal.domain.server.CloudCredentialsRepository
import com.datocal.nolocal.domain.server.CloudFlavor
import com.datocal.nolocal.domain.server.EncryptionService

class SetUpCredentialsUseCase(
    private val cloudCredentialsRepository: CloudCredentialsRepository,
    private val encryptionService: EncryptionService,
) {
    fun execute(request: SetUpCredentialsUseCaseRequest) {
        val cloudCredentials =
            CloudCredentials(
                Account(request.userId, request.tenant),
                request.flavor,
                encryptionService.encrypt(request.token, request.encryptionKey),
            )

        cloudCredentialsRepository.save(cloudCredentials)
    }
}

data class SetUpCredentialsUseCaseRequest(
    val userId: String,
    val tenant: Tenant,
    val encryptionKey: String,
    val token: String,
    val flavor: CloudFlavor,
)

package com.datocal.nolocal.application.server

import com.datocal.nolocal.domain.account.Account
import com.datocal.nolocal.domain.account.Tenant
import com.datocal.nolocal.domain.server.CloudCredentials
import com.datocal.nolocal.domain.server.CloudCredentialsRepository
import com.datocal.nolocal.domain.server.CloudFlavor
import com.datocal.nolocal.domain.server.EncryptionService
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CreateCloudCredentialsUseCaseTest {
    private val encryptionService: EncryptionService = mock()
    private val repository: CloudCredentialsRepository = mock()
    private val useCase = SetUpCredentialsUseCase(repository, encryptionService)

    private companion object {
        private const val CLOUD_TOKEN = "abcd"
        private const val ENCRYPTION_KEY = "blabla"
        private const val ENCRYPTED_TOKEN = "ENCRYPTED"
        private const val USER_ID = "Example#1234"
    }

    @Test
    fun `should save account`() {
        val givenRequest =
            SetUpCredentialsUseCaseRequest(
                userId = USER_ID,
                tenant = Tenant.DISCORD,
                encryptionKey = ENCRYPTION_KEY,
                token = CLOUD_TOKEN,
                flavor = CloudFlavor.DIGITAL_OCEAN,
            )
        whenever(encryptionService.encrypt(CLOUD_TOKEN, ENCRYPTION_KEY)).thenReturn(ENCRYPTED_TOKEN)

        useCase.execute(givenRequest)

        verify(
            repository,
        ).save(CloudCredentials(Account(USER_ID, Tenant.DISCORD), encryptedToken = ENCRYPTED_TOKEN, flavor = CloudFlavor.DIGITAL_OCEAN))
    }
}

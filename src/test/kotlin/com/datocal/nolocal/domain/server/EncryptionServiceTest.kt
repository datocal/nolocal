package com.datocal.nolocal.domain.server

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class EncryptionServiceTest {
    private val encryptionService = EncryptionService()

    private companion object {
        private const val STRING_TO_ENCRYPT = "Super-secret-password"
        private const val ENCRYPTION_KEY = "easierPassword!!"
        private const val SHORT_ENCRYPTION_KEY = "123"
    }

    @Test
    fun `should encrypt and decrypt a string`() {
        val encryptedSecret = encryptionService.encrypt(STRING_TO_ENCRYPT, ENCRYPTION_KEY)
        val decryptedSecret = encryptionService.decrypt(encryptedSecret, ENCRYPTION_KEY)

        Assertions.assertNotEquals(STRING_TO_ENCRYPT, encryptedSecret)
        Assertions.assertEquals(STRING_TO_ENCRYPT, decryptedSecret)
    }

    @Test
    fun `should throw exception when encryption key is short`() {
        Assertions.assertThrows(PasswordNotLongEnoughException::class.java) {
            encryptionService.encrypt(STRING_TO_ENCRYPT, SHORT_ENCRYPTION_KEY)
        }
    }
}

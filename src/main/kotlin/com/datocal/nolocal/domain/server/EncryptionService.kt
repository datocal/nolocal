package com.datocal.nolocal.domain.server

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class EncryptionService {
    fun encrypt(
        input: String,
        passphrase: String,
    ): String {
        if (passphrase.length < 16) {
            throw PasswordNotLongEnoughException()
        }
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(passphrase.toByteArray(), "AES"))
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(
        input: String,
        passphrase: String,
    ): String {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(passphrase.toByteArray(), "AES"))
        val encryptedBytes = Base64.getDecoder().decode(input)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }
}

class PasswordNotLongEnoughException : Exception()

package com.datocal.nolocal.domain.server

enum class CloudFlavor(private val code: String) {
    DIGITAL_OCEAN("digitalocean"),
    ;

    override fun toString(): String {
        return code
    }

    companion object {
        fun fromValue(code: String): CloudFlavor {
            return CloudFlavor.values().find { it.code == code } ?: throw NonExistingCloudFlavorException()
        }
    }
}

class NonExistingCloudFlavorException : Exception()

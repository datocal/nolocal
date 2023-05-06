package com.datocal.nolocal.domain.server

enum class CloudFlavor(private val code: String) {
    DIGITAL_OCEAN("digitalocean"),
    ;

    override fun toString(): String {
        return code
    }
}

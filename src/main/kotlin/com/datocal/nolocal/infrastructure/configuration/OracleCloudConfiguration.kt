package com.datocal.nolocal.infrastructure.configuration

import com.oracle.bmc.Region
import com.oracle.bmc.auth.AuthenticationDetailsProvider
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider
import com.oracle.bmc.auth.StringPrivateKeySupplier
import com.oracle.bmc.identity.IdentityClient
import com.oracle.bmc.identity.requests.ListRegionsRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.Base64

@Configuration
class OracleCloudConfiguration {
    private val logger: Logger = LoggerFactory.getLogger(OracleCloudConfiguration::class.java)

    @Value("\${cloud.oracle.tenant-id}")
    private lateinit var tenantId: String

    @Value("\${cloud.oracle.user-id}")
    private lateinit var userId: String

    @Value("\${cloud.oracle.fingerprint}")
    private lateinit var fingerprint: String

    @Value("\${cloud.oracle.private-key}")
    private lateinit var privateKey: String

    @Bean
    fun authenticationDetailsProvider(): AuthenticationDetailsProvider {
        return SimpleAuthenticationDetailsProvider.builder()
            .tenantId(tenantId)
            .userId(userId)
            .fingerprint(fingerprint)
            .privateKeySupplier(StringPrivateKeySupplier(String(Base64.getDecoder().decode(privateKey))))
            .build()
    }

    @Bean
    fun identityClient(): IdentityClient {
        val identityClient =
            IdentityClient.builder()
                .region(Region.UK_LONDON_1)
                .build(authenticationDetailsProvider())
        identityClient.initService()
        return identityClient
    }

    private fun IdentityClient.initService() {
        val regions = this.listRegions(ListRegionsRequest.builder().build())
        logger.warn("Oracle cloud service initialized: " + regions.items)
    }
}

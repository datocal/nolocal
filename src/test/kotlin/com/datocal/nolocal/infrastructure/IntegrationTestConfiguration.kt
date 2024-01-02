package com.datocal.nolocal.infrastructure

import com.oracle.bmc.auth.AuthenticationDetailsProvider
import com.oracle.bmc.identity.IdentityClient
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean

@TestConfiguration
class IntegrationTestConfiguration {

    @MockBean
    private lateinit var identityClient: IdentityClient

    @MockBean
    private lateinit var authenticationDetailsProvider: AuthenticationDetailsProvider
}

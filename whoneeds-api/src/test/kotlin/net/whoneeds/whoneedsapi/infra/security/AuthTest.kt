package net.whoneeds.whoneedsapi.infra.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.LOGIN_ROUTE
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.UserData.EMAIL
import net.whoneeds.whoneedsapi.UserData.PASSWORD
import net.whoneeds.whoneedsapi.adapters.api.user.UserAccountRepository
import net.whoneeds.whoneedsapi.domain.model.UserAccount
import net.whoneeds.whoneedsapi.infra.security.jwt.AuthenticationResponse
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtCodecService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class AuthTest
@Autowired constructor(
        private val userRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtCodec: JwtCodecService,
        private val mvc: MockMvc
) {
    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    internal fun setUp() {
        userRepository.save(UserAccount(
                email = EMAIL,
                password = passwordEncoder.encode(PASSWORD))
        )
    }

    @Test
    internal fun `should return UNAUTTHORIZED when unauthorized user requests secured endpoint`() {
        mvc.perform(
                get(USERS_ROUTE)
        ).andExpect { status().isUnauthorized }
    }

    @Test
    internal fun `should authenticate the user when logging in with valid credentials`() {
        mvc.perform(
                post(LOGIN_ROUTE).content("""
                    {
                        "email": "$EMAIL",
                        "password": "$PASSWORD"
                    }
                """.trimIndent())
        ).andExpect {
            status().isOk
        }.andDo {
            val parsedResponse: AuthenticationResponse = objectMapper.readValue(it.response.contentAsString)
            assertThat(parsedResponse.type)
                    .isEqualTo("Bearer")
            assertThat(parsedResponse.access)
                    .matches { token -> jwtCodec.parseJwt(token).body.subject == EMAIL }
        }
    }

    @Test
    internal fun `should return UNAUTHORIZED when logging in with invalid credentials`() {
        mvc.perform(
                post(LOGIN_ROUTE).content("""
                    {
                        "email": "$EMAIL",
                        "password": "invalid"
                    }
                """.trimIndent())
        ).andExpect { status().isUnauthorized }
    }

    @Test
    internal fun `should allow authenticated user to access secured endpoint`() {
        mvc.perform(
                post(LOGIN_ROUTE).content("""
                    {
                        "email": "$EMAIL",
                        "password": "$PASSWORD"
                    }
                """.trimIndent())
        )
        mvc.perform(
                get(USERS_ROUTE)
        ).andExpect { status().isOk }
    }
}

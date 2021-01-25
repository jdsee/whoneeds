package net.whoneeds.whoneedsapi.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.LOGIN_ROUTE
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.LOGOUT_ROUTE
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.UserData.EMAIL
import net.whoneeds.whoneedsapi.UserData.PASSWORD
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtBlockListRepository
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import net.whoneeds.whoneedsapi.domain.ports.users.UserAccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
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
        private val jwtBlockListRepo: JwtBlockListRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtService: JwtService,
        private val mvc: MockMvc
) {
    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() {
        userRepository.save(UserAccount(
                email = EMAIL,
                password = passwordEncoder.encode(PASSWORD))
        )
    }

    @Test
    fun `should return UNAUTTHORIZED when unauthorized user requests secured endpoint`() {
        mvc.perform(
                get(USERS_ROUTE)
        ).andExpect { status().isUnauthorized }
    }

    @Test
    fun `should authenticate the user when logging in with valid credentials`() {
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
                    .matches { token -> jwtService.parseJwt(token).body.subject == EMAIL }
        }
    }

    @Test
    fun `should return UNAUTHORIZED when logging in with invalid credentials`() {
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
    fun `should allow authenticated user to access secured endpoint`() {
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

    @WithMockUser
    @Test
    fun `should add the users jwt to the block list when logging out`() {
        val authentication = authenticateUser()
        mvc.perform(
                post(LOGOUT_ROUTE).header(HttpHeaders.AUTHORIZATION, authentication.access)
        ).andExpect {
            status().isOk
            assertThat(jwtBlockListRepo.existsByToken(authentication.access)).isTrue()
        }
    }

    @WithMockUser
    @Test
    fun `should not allow access for requests with invalid jwt`() {
        val authentication = authenticateUser()
        mvc.perform(
                post(LOGOUT_ROUTE).header(HttpHeaders.AUTHORIZATION, authentication.access)
        )
        mvc.perform(
                get(USERS_ROUTE).header(HttpHeaders.AUTHORIZATION, authentication.access)
        ).andExpect {
            status().isUnauthorized
        }
    }

    private fun authenticateUser(): AuthenticationResponse {
        val response = mvc.perform(
                post(LOGIN_ROUTE).content("""
                    {
                        "email": "$EMAIL",
                        "password": "$PASSWORD"
                    }
                """.trimIndent())
        ).andReturn().response
        return objectMapper.readValue(response.contentAsString)
    }
}

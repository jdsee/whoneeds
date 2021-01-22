package net.whoneeds.whoneedsapi.adapters.api.users

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.UserData.EMAIL
import net.whoneeds.whoneedsapi.UserData.PASSWORD
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.ports.users.UserAccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional


/**
 * @author Joscha Seelig <jduesentrieb> 2021
 **/
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class UserControllerTest
@Autowired constructor(
        private val mvc: MockMvc,
        private val userRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder
) {
    @Test
    internal fun `should register new user on post request with valid credentials`() {
        mvc.perform(
                post(USERS_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "$EMAIL",
                                "password": "$PASSWORD"
                            }
                        """.trimIndent())
        ).andExpect {
            status().isCreated
        }.andDo {
            assertThat(userRepository
                    .findById(it.response.contentAsString.toLong()).get().password)
                    .matches { storedPw -> passwordEncoder.matches(PASSWORD, storedPw) }
        }
    }

    @WithMockUser(username = EMAIL)
    @Test
    internal fun `should return actually authorized user without password when requesting profile endpoint`() {
        userRepository.save(UserAccount(email = EMAIL, password = PASSWORD))

        mvc.perform(
                get("$USERS_ROUTE/me")
        ).andExpect {
            status().isOk
        }.andDo {
            assertThat(it.response.contentAsString)
                    .contains(EMAIL)
                    .doesNotContain("password: $PASSWORD")
        }
    }
}
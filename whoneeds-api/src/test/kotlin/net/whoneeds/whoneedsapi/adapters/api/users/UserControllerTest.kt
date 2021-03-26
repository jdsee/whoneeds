package net.whoneeds.whoneedsapi.adapters.api.users

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.UserData.EMAIL
import net.whoneeds.whoneedsapi.UserData.NAME
import net.whoneeds.whoneedsapi.UserData.PASSWORD
import net.whoneeds.whoneedsapi.UserData.SURNAME
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
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
    fun `should register new user on post request with valid credentials`() {
        mvc.post(USERS_ROUTE) {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "email": "$EMAIL",
                    "password": "$PASSWORD",
                    "name": "$NAME",
                    "surname": "$SURNAME"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            val expectedId = userRepository.findAll()[0].id!!
            header {
                this.string(HttpHeaders.LOCATION, Matchers.matchesPattern(".*/users/$expectedId"))
            }
            assertThat(userRepository.findById(expectedId).get().password)
                    .matches { storedPw -> passwordEncoder.matches(PASSWORD, storedPw) }
        }
    }

    @WithMockUser(username = EMAIL)
    @Test
    fun `should return actually authorized user without password when requesting profile endpoint`() {
        userRepository.save(UserAccount(EMAIL, PASSWORD, NAME, SURNAME))

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

    @Test
    fun `should not register user with already present email`() {
        userRepository.save(UserAccount(EMAIL, PASSWORD, NAME, SURNAME))

        mvc.perform(
                post(USERS_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "$EMAIL",
                                "password": "$PASSWORD",
                                "name": "$NAME",
                                "surname": "$SURNAME"
                            }
                        """.trimIndent())
        ).andExpect {
            status().isConflict
        }
    }

    @Test
    fun `should not register user with invalid email`() {
        mvc.perform(
                post(USERS_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "-@mail-invalid.com",
                                "password": "$PASSWORD",
                                "name": "$NAME",
                                "surname": "$SURNAME"
                            }
                        """.trimIndent())
        ).andExpect {
            status().isBadRequest
        }
    }

    @Test
    fun `should not register user with password with less than 8 characters`() {
        mvc.perform(
                post(USERS_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "$EMAIL",
                                "password": "2short",
                                "name": ",$NAME,",
                                "surname": ",$SURNAME,"
                            }
                        """.trimIndent())
        ).andExpect {
            status().isBadRequest
        }
    }

    @ParameterizedTest
    @CsvSource(value = [
        "mail@web.com, ,name,surname",
        "mail@web.com,password, ,surname",
        "mail@web.com,password,name, ",
        " ,password,name,surname",
    ])
    internal fun `should not register user with missing required fields`(
            email: String?, password: String?, name: String?, surname: String?
    ) {
        mvc.perform(
                post(USERS_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "email": "$email",
                                "password": "$password",
                                "name": ",$name,",
                                "surname": ",$surname,"
                            }
                        """.trimIndent())
        ).andExpect {
            status().isBadRequest
        }
    }

    // TODO: google +1 mail regex test
}

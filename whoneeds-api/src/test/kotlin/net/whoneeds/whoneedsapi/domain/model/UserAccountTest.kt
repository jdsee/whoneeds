package net.whoneeds.whoneedsapi.domain.model

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object Expected {
    const val EMAIL_ADDRESS = "me@www.com"
    const val PASSWORD = "secret"
}

/**
 * @author Joscha Seelig <jduesentrieb> 2021
 */
internal class UserAccountTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should deserialize login request properly`() {
        val validJson = UserAccountTest::class.java.getResource("validLoginRequest.json")
        val deserializedJson = objectMapper.readValue(validJson, UserAccount::class.java)

        assertThat(deserializedJson)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(UserAccount(
                        email = Expected.EMAIL_ADDRESS,
                        password = Expected.PASSWORD))
    }
}
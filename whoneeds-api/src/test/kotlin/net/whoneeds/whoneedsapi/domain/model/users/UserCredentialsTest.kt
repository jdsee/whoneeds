package net.whoneeds.whoneedsapi.domain.model.users

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

object Expected {
    const val EMAIL_ADDRESS = "me@www.com"
    const val PASSWORD = "worldwide"
}

/**
 * @author Joscha Seelig <jduesentrieb> 2021
 */
internal class UserCredentialsTest {

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `should deserialize login request properly`() {
        val validJson = UserCredentialsTest::class.java.getResource("validLoginRequest.json")
        val deserializedJson = objectMapper.readValue(validJson, UserCredentials::class.java)

        assertThat(deserializedJson)
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(UserCredentials(
                        email = Expected.EMAIL_ADDRESS,
                        password = Expected.PASSWORD))
    }
}

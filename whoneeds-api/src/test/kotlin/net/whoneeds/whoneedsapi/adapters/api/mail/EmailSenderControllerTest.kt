package net.whoneeds.whoneedsapi.adapters.api.mail

import net.whoneeds.whoneedsapi.MailData
import net.whoneeds.whoneedsapi.RoutingEndpointConstants
import net.whoneeds.whoneedsapi.UserData
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.internal.MagicApiIntrinsics


/**
 * @author Lukas Schuetz <pomcom> 2021
</pomcom> */
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
internal class EmailSenderControllerTest
@Autowired

constructor(
        private val mvc: MockMvc,
) {

    @Test
    fun sendSimpleEmail() {
        mvc.perform(
                post(RoutingEndpointConstants.LOGIN_ROUTE).content("""
                    {
                        "subject": "${MailData.SUBJECT}",
                        "targetMail": "${UserData.EMAIL}"
                        "text": "${MailData.MESSAGE}
                    }
                """.trimIndent())
        ).andExpect {
            MockMvcResultMatchers.status().isAccepted
        }

        @Test
        fun sendSimpleTemplateEmail() {
        }

        @Test
        fun sendEmailWithAttachment() {
        }
    }
}


data class TestData(
        val subject: String = "TestSubject",
        val targetEmail: String = "test@test.com",
        val text: String = "That's a testmessage.",
)

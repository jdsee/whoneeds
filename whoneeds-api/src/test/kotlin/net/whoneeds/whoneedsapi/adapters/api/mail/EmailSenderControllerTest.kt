package net.whoneeds.whoneedsapi.adapters.api.mail

import net.whoneeds.whoneedsapi.MailData
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.SEND_MAIL
import net.whoneeds.whoneedsapi.UserData
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.transaction.annotation.Transactional


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

    @WithMockUser
    @Test
    fun sendSimpleEmail() {
        mvc.post(SEND_MAIL) {
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "subject": "${MailData.SUBJECT}",
                "targetEmail": "${UserData.EMAIL}",
                "text": "${MailData.MESSAGE}",
                "name": "${MailData.NAME}"
            }""".trimIndent()
        }.andExpect {
            status { isAccepted() }
        }
    }

    @WithMockUser
    @Test
    fun sendSimpleEmailWithoutName() {
        mvc.post(SEND_MAIL) {
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "subject": "${MailData.SUBJECT}",
                "targetEmail": "${UserData.EMAIL}",
                "text": "${MailData.MESSAGE}"
            }""".trimIndent()
        }.andExpect {
            status { isAccepted() }
        }
    }


}

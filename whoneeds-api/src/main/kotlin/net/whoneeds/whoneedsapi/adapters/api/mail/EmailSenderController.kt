package net.whoneeds.whoneedsapi.adapters.api.mail

/**
 * @author Lukas Schuetz <pomcom> 2021
 */

import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_MAIL
import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_TEMPLATE
import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_WITH_ATTACHMENT
import net.whoneeds.whoneedsapi.infra.mail.model.EmailRequest
import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailSenderController(
        private val emailSenderService: EmailSenderService
) {

    @PostMapping(SEND_MAIL)
    fun sendSimpleEmail(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmail(
                subject = request.subject,
                targetEmail = request.targetEmail,
                text = request.text
        )

        return ResponseEntity.noContent().build()
    }

    @PostMapping(SEND_TEMPLATE)
    fun sendSimpleTemplateEmail(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmailUsingTemplate(
                name = request.name,
                targetEmail = request.targetEmail
        )

        return ResponseEntity.noContent().build()
    }

    @PostMapping(SEND_WITH_ATTACHMENT)
    fun sendEmailWithAttachment(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmailWithAttachment(
                targetEmail = request.targetEmail
        )

        return ResponseEntity.noContent().build()
    }
}

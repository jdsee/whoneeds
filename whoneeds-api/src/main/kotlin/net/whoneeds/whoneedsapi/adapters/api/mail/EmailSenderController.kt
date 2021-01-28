package net.whoneeds.whoneedsapi.adapters.api.mail

/**
 * @author Lukas Schuetz <pomcom> 2021
 */

import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_MAIL
import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_TEMPLATE
import net.whoneeds.whoneedsapi.EmailSenderConstants.SEND_WITH_ATTACHMENT
import net.whoneeds.whoneedsapi.domain.ports.mail.EmailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmailSenderController(
        private val emailSenderService: EmailSenderService
) {
    /**
     * Sends a email with just subject and text
     */
    @PostMapping(SEND_MAIL)
    fun sendSimpleEmail(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmail(
                subject = request.subject,
                targetEmail = request.targetEmail,
                text = request.text
        )

        return ResponseEntity.accepted().build()
    }

    /**
     * Sends an email with a predefined template text
     * TODO possible use for upcoming newsletter feature
     */
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

    /**
     * Sends an email with attachment
     */
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

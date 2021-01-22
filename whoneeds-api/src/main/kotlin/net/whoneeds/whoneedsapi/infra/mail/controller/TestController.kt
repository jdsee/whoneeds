package net.whoneeds.whoneedsapi.infra.mail.controller

/**
 * @author Lukas Schuetz <pomcom> 2021
 */

import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
        private val emailSenderService: EmailSenderService
) {

    @PostMapping("/email")
    fun sendSimpleEmail(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmail(
                subject = request.subject!!,
                targetEmail = request.targetEmail!!,
                text = request.text!!
        )

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/email/template")
    fun sendSimpleTemplateEmail(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmailUsingTemplate(
                name = request.name!!,
                targetEmail = request.targetEmail!!
        )

        return ResponseEntity.noContent().build()
    }

    @PostMapping("/email/attachment")
    fun sendEmailWithAttachment(
            @RequestBody request: EmailRequest
    ): ResponseEntity<Void> {
        emailSenderService.sendEmailWithAttachment(
                targetEmail = request.targetEmail!!
        )

        return ResponseEntity.noContent().build()
    }
}

class EmailRequest(
        val subject: String?,
        val targetEmail: String?,
        val text: String?,
        val name: String?
)

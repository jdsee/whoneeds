package net.whoneeds.whoneedsapi.adapters.api.pwReset

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.adapters.api.user.UserAccountRepository
import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import net.whoneeds.whoneedsapi.infra.resetPassword.service.PrepareResetService
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtCodecService
import org.springframework.http.HttpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI


/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private val mailService: EmailSenderService,
        private val prepareResetService: PrepareResetService
) {

    /**
     * Open api route
     * Provides resetPassword url vor user
     * Always returns 202 for security reasons
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody userMail: MailForwardingReq) {

        println("-----------------------USER MAIL--------------------------------------------")
        println(userMail.mailTo)

        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")

        println("----------------------------TOKEN--------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")

        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")
        println("-------------------------------------------------------------------")


        val test = prepareResetService.prepareReset(userMail)


        mailService.sendEmail(
                subject = "Reset whoneeds password",
                targetEmail = userMail.mailTo,
                text = test
        )

    }

    /*
    URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
     */



}

data class MailForwardingReq(val mailTo: String)

/*
mailMessage.setText("To complete the password reset process, please click here: "
              + "http://localhost:8082/confirm-reset?token="+confirmationToken.getConfirmationToken
 */
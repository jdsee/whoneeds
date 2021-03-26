package net.whoneeds.whoneedsapi.adapters.api.resetPassword

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.domain.ports.mail.EmailSenderService
import net.whoneeds.whoneedsapi.domain.ports.users.PreparePasswordResetService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private val mailService: EmailSenderService,
        private val preparePasswordResetService: PreparePasswordResetService
) {
    /**
     * Open api route
     * Provides resetPassword url vor user
     * Always returns 202 for security reasons
     * //TODO logging for api requests
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody userMail: MailForwardingReq, uriBuilder: UriComponentsBuilder) {
        mailService.sendEmail(
                subject = "Reset whoneeds password",
                targetEmail = userMail.mailTo,
                text = "Please follow the link for your pw reset: " + preparePasswordResetService.prepareReset(userMail, uriBuilder).toString()
        )
    }
}

data class MailForwardingReq(val mailTo: String)

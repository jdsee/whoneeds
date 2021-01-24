package net.whoneeds.whoneedsapi.adapters.api.pwReset

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import net.whoneeds.whoneedsapi.infra.resetPassword.service.PrepareResetService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*

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
     * //TODO logging for api requests
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody userMail: MailForwardingReq) {

        mailService.sendEmail(
                subject = "Reset whoneeds password",
                targetEmail = userMail.mailTo,
                text = "Please follow link for pw reset: " + prepareResetService.prepareReset(userMail).toString()
        )
    }

}

data class MailForwardingReq(val mailTo: String)

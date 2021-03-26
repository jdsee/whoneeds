package net.whoneeds.whoneedsapi.adapters.api.resetPassword

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.domain.ports.users.PreparePasswordResetService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private val preparePasswordResetService: PreparePasswordResetService
) {
    /**
     * Open api route
     * Provides resetPassword url vor user
     * Always returns 202 for security reasons
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody userMail: MailForwardingReq, httpRequest: HttpServletRequest) {
        preparePasswordResetService.notifyUser(userMail.emailRecipient, httpRequest.getHeader(HttpHeaders.ORIGIN))
    }
}

data class MailForwardingReq(val emailRecipient: String)

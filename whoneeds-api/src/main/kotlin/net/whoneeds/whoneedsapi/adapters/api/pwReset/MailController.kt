package net.whoneeds.whoneedsapi.adapters.api.pwReset

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.adapters.api.user.UserAccountRepository
import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*


/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private val userAccountRepository: UserAccountRepository,
        private val mailService: EmailSenderService
) {


    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody req: MailForwardingReq) {
        println(req.mailTo)
        mailService.sendEmail("hi", "das ist ein text", req.mailTo)
    }
}

data class MailForwardingReq(val mailTo: String)



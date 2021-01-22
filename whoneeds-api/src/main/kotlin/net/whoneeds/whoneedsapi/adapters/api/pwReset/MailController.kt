package net.whoneeds.whoneedsapi.adapters.api.pwReset

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.adapters.api.user.UserAccountRepository
import net.whoneeds.whoneedsapi.domain.model.UserAccount
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest


/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private val userAccountRepository: UserAccountRepository
) {


    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody req: ResetPasswordReq) {
        println(req.mail)
    }


}

data class MailForwardingReq(val mailTo: String)
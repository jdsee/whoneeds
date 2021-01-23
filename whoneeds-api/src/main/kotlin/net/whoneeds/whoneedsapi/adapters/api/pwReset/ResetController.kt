package net.whoneeds.whoneedsapi.adapters.api.pwReset

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.adapters.api.user.UserAccountRepository
import net.whoneeds.whoneedsapi.domain.model.UserAccount
import net.whoneeds.whoneedsapi.infra.mail.service.EmailSenderService
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtCodecService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@RestController
@RequestMapping(RESET_PASSWORD_ROUTE)
class ResetController(
        private var jwtService: JwtCodecService,
        private val userAccountRepository: UserAccountRepository,
        private val mailService: EmailSenderService
) {

    /**
     * Provides resetPassword url vor user
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun resetPassword(@RequestBody req: MailForwardingReq) {


        println(req.mailTo)
        val userAcc = userAccountRepository.findByEmail(req.mailTo)

        userAcc?.let { jwtService.generateJwt(it.username ?: " ") }
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "So nicht Freundchen.")


        print(jwtService.generateJwt(userAcc.username.toString()))

        mailService.sendEmail(
                subject = "Password zurücksetzen",
                targetEmail = req.mailTo,
                text = "Link...abc"
        )
    }

}

data class MailForwardingReq(val mailTo: String)

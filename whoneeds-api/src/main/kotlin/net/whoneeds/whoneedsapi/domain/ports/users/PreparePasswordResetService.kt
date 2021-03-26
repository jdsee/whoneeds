package net.whoneeds.whoneedsapi.domain.ports.users

import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import net.whoneeds.whoneedsapi.domain.ports.mail.EmailSenderService
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@Service
class PreparePasswordResetService(
        private var jwtService: JwtService,
        private var mailService: EmailSenderService
) {

    fun notifyUser(mailReceiver: String, clientLocation: String) {
        val path = UriComponentsBuilder.fromHttpUrl(clientLocation).path("/changePassword")
                .queryParam("token", jwtService.generatePwResetLinkJwt(mailReceiver)).build().toUri()
        mailService.sendEmail(
                subject = "Reset whoneeds password",
                targetEmail = mailReceiver,
                text = "Please follow the link for your pw reset: $path/"
        )
    }
}

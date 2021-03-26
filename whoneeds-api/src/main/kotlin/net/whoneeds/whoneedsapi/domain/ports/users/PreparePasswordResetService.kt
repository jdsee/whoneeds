package net.whoneeds.whoneedsapi.domain.ports.users

import net.whoneeds.whoneedsapi.adapters.api.resetPassword.MailForwardingReq
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@Service
class PreparePasswordResetService(
        private var jwtService: JwtService,
) {

    fun prepareReset(userMail: MailForwardingReq,
                     uriBuilder: UriComponentsBuilder): URI {
        return uriBuilder.path("changePassword")
                .queryParam("token", jwtService.generatePwResetLinkJwt(userMail.mailTo)).build().toUri()
    }
}

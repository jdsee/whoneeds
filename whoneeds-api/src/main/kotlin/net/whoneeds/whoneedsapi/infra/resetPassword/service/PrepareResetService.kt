package net.whoneeds.whoneedsapi.infra.resetPassword.service

import net.whoneeds.whoneedsapi.adapters.api.resetPassword.MailForwardingReq
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder.fromHttpUrl
import java.net.URI

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@Service
class PrepareResetService(
        private var jwtService: JwtService,
        private var userAccountRepository: UserAccountRepository,
) {

    fun prepareReset(userMail: MailForwardingReq): URI {
        val usr = userAccountRepository.findByEmail(userMail.mailTo)
        println(usr)
        //TODO get frontend url
        return fromHttpUrl("http://localhost:3000/changePassword")
                .queryParam("token", jwtService.generatePwResetLinkJwt(userMail.mailTo))
                .build()
                .toUri()
    }
}

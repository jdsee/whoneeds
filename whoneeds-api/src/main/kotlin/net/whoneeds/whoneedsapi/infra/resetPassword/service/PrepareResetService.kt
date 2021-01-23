package net.whoneeds.whoneedsapi.infra.resetPassword.service

import net.whoneeds.whoneedsapi.adapters.api.pwReset.MailForwardingReq
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import net.whoneeds.whoneedsapi.domain.ports.users.UserAccountRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.*
import org.springframework.web.util.UriComponentsBuilder

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
@Service
class PrepareResetService(
        private var jwtService: JwtService,
        private var userAccountRepository: UserAccountRepository
) {

    fun prepareReset(userMail: MailForwardingReq): String {

        val token = userAccountRepository.findByEmail(userMail.mailTo)?.let {
            jwtService.generateResetPasswordJwt(it.username ?: "")

        }

        print(token)

        return tokenFun(token)
    }

    private fun createResetLinkWithToken(token: String): ResponseEntity<Any> {

        val resetLink = UriComponentsBuilder.fromPath("/whoneeds/changePassword?token=")
                .buildAndExpand(token).toUri()
        return ResponseEntity.created(resetLink).build()
    }

    private fun tokenFun(token: String?): String {
        return fromHttpUrl("http://localhost:3000/whoneeds/changePassword?token=")
                .buildAndExpand(token).toUriString()
    }

    private fun createResetLinkWithoutToken(): String {
        return fromHttpUrl("http://localhost:3000/whoneeds/changePassword").toUriString()
    }
}
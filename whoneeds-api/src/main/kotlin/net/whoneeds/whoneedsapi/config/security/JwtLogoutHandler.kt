package net.whoneeds.whoneedsapi.config.security

import net.whoneeds.whoneedsapi.domain.model.jwt.InvalidatedJwt
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtBlockListRepository
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtLogoutHandler(
        private val jwtBlockListRepo: JwtBlockListRepository,
        private val jwtService: JwtService
) : SimpleUrlLogoutSuccessHandler(), LogoutSuccessHandler {

    // TODO check why authentication might be null
    override fun onLogoutSuccess(request: HttpServletRequest,
                                 response: HttpServletResponse?,
                                 authentication: Authentication?) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)?.split(" ")?.last()
        token?.let {
            jwtBlockListRepo.save(InvalidatedJwt(
                    token = it,
                    expiry = jwtService.parseJwt(it).body.expiration)
            )
        } ?: throw ResponseStatusException(HttpStatus.FORBIDDEN)
    }
}

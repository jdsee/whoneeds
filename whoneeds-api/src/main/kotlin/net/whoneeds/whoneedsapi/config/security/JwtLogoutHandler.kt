package net.whoneeds.whoneedsapi.config.security

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
        private val jwtService: JwtService
) : SimpleUrlLogoutSuccessHandler(), LogoutSuccessHandler {

    override fun onLogoutSuccess(request: HttpServletRequest,
                                 response: HttpServletResponse?,
                                 authentication: Authentication?) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)?.split(" ")?.last()
                ?: throw ResponseStatusException(HttpStatus.FORBIDDEN)
        jwtService.invalidateToken(token)
    }
}

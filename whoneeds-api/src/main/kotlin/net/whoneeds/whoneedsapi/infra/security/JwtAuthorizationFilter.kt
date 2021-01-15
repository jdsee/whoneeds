package net.whoneeds.whoneedsapi.infra.security

import net.whoneeds.whoneedsapi.config.jwt.JwtAuthenticationService
import net.whoneeds.whoneedsapi.infra.security.SecurityConstants.BEARER_TOKEN_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
class JwtAuthorizationFilter(authManager: AuthenticationManager,
                             private val jwtService: JwtAuthenticationService)
    : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == null || !header.isBearerToken()) {
            chain.doFilter(request, response)
            return
        }

        getAuthentication(header)
    }

    private fun getAuthentication(authorizationHeader: String): UsernamePasswordAuthenticationToken {
        val userId = jwtService.parseJwt(authorizationHeader.extractToken()).body.subject
        return UsernamePasswordAuthenticationToken(userId, null, emptyList())
    }

    private fun String.extractToken() = split(" ").last()
    private fun String.isBearerToken() = startsWith(BEARER_TOKEN_PREFIX)
}
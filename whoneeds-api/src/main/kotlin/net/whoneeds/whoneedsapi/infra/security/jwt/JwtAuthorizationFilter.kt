package net.whoneeds.whoneedsapi.infra.security.jwt

import net.whoneeds.whoneedsapi.SecurityConstants.BEARER_TOKEN_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
class JwtAuthorizationFilter(authManager: AuthenticationManager,
                             private val jwtService: JwtCodecService)
    : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == null || !header.isBearerToken()) {
            chain.doFilter(request, response)
            return
        }

        SecurityContextHolder.getContext().authentication = getAuthentication(header)
        chain.doFilter(request, response)
    }

    private fun getAuthentication(authorizationHeader: String): UsernamePasswordAuthenticationToken {
        val userId = jwtService.parseJwt(authorizationHeader.extractToken()).body.subject
        return UsernamePasswordAuthenticationToken(userId, null, emptyList())
    }

    private fun String.extractToken() = split(" ").last()
    private fun String.isBearerToken() = startsWith(BEARER_TOKEN_PREFIX)
}
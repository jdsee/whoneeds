package net.whoneeds.whoneedsapi.infrastructure.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.whoneeds.whoneedsapi.config.jwt.JwtAuthenticationService
import net.whoneeds.whoneedsapi.domain.model.UserAccount
import net.whoneeds.whoneedsapi.infrastructure.security.SecurityConstants.BEARER_TOKEN_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
class JwtAuthenticationFilter(authManager: AuthenticationManager,
                              private val jwtService: JwtAuthenticationService)
    : UsernamePasswordAuthenticationFilter(authManager) {

    private val objectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse): Authentication {
        val user = request?.inputStream?.let { objectMapper.readValue<UserAccount>(it) }

        return authenticationManager.authenticate(user?.toAuthenticationToken())
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse,
                                          chain: FilterChain?, auth: Authentication?) {
        val jwt = jwtService.generateJwt((auth?.principal as User).username)
        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_TOKEN_PREFIX + jwt)
    }

    private fun UserAccount.toAuthenticationToken()
            : UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(username, password, emptyList())
}
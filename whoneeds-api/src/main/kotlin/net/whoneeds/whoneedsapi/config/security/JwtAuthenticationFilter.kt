package net.whoneeds.whoneedsapi.config.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.SecurityConstants.BEARER_TOKEN_PREFIX
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
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
class JwtAuthenticationFilter(
        authManager: AuthenticationManager,
        private val jwtService: JwtService)
    : UsernamePasswordAuthenticationFilter(authManager) {

    private val objectMapper = jacksonObjectMapper()

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val user = request.inputStream?.let { objectMapper.readValue<UserAccount>(it) }

        return authenticationManager.authenticate(user?.toAuthenticationToken())
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse,
                                          chain: FilterChain?, auth: Authentication?) {
        val subject = (auth?.principal as User).username
        val jwt = jwtService.generateJwt(subject)
        val authResponse = objectMapper.writeValueAsString(
                AuthenticationResponse(access = jwt)
        )
        response.writer.print(authResponse)
    }

    private fun UserAccount.toAuthenticationToken() =
            UsernamePasswordAuthenticationToken(email, password, emptyList())
}

data class AuthenticationResponse(
        val access: String,
        val refresh: String? = null,
        val type: String = BEARER_TOKEN_PREFIX
)

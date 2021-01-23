package net.whoneeds.whoneedsapi.infra.security

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtCodecService
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtAuthenticationEntryPoint
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtAuthenticationFilter
import net.whoneeds.whoneedsapi.infra.security.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@EnableWebSecurity
class SecurityConfig(
        private val userDetailsService: PersistenceUserDetailsService,
        private val jwtService: JwtCodecService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfig = CorsConfiguration()
        corsConfig.applyPermitDefaultValues().addExposedHeader(HttpHeaders.AUTHORIZATION)
        source.registerCorsConfiguration("/**", corsConfig)
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USERS_ROUTE, RESET_PASSWORD_ROUTE).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JwtAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(JwtAuthorizationFilter(authenticationManager(), jwtService))
                .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}

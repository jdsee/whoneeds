package net.whoneeds.whoneedsapi.config.security

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.RESET_PASSWORD_ROUTE
import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.jwt.JwtBlockListRepository
import net.whoneeds.whoneedsapi.domain.ports.jwt.JwtService
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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.time.Duration


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@EnableWebSecurity
class SecurityConfig(
        private val userDetailsService: PersistenceUserDetailsService,
        private val jwtService: JwtService,
        private val jwtBlockListRepo: JwtBlockListRepository
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun logoutSuccessHandler(): LogoutSuccessHandler? {
        return JwtLogoutHandler(jwtService)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.addAllowedOriginPattern("*")
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        config.setMaxAge(Duration.ofHours(24))
        config.addExposedHeader(HttpHeaders.AUTHORIZATION)
        config.applyPermitDefaultValues()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, USERS_ROUTE, RESET_PASSWORD_ROUTE).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(JwtAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(JwtAuthorizationFilter(authenticationManager(), jwtService, jwtBlockListRepo))
                .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}

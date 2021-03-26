package net.whoneeds.whoneedsapi.domain.ports.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import net.whoneeds.whoneedsapi.domain.model.jwt.InvalidatedJwt
import net.whoneeds.whoneedsapi.domain.model.jwt.JwtBlockListRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class JwtService(
        @Value("\${jwt.default.expiration}")
        private val defaultExpiration: Duration,
        private val jwtBlockListRepo: JwtBlockListRepository
) {
    private val algorithm = SignatureAlgorithm.RS256
    private val keyPair: KeyPair = Keys.keyPairFor(algorithm)


    fun generateJwt(subject: String, expiration: Duration = defaultExpiration): String = Jwts.builder()
            .setSubject(subject)
            .setExpiration(Date.from(Instant.now().plus(defaultExpiration)))
            .setIssuedAt(Date.from(Instant.now()))
            .signWith(keyPair.private, algorithm)
            .compact()

    fun parseJwt(jwt: String): Jws<Claims> = Jwts.parserBuilder()
            .setSigningKey(keyPair.public)
            .build()
            .parseClaimsJws(jwt)

    fun generatePwResetLinkJwt(subject: String): String =
            Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(Date.from(Instant.now().plus(defaultExpiration)))
                    .setIssuedAt(Date.from(Instant.now()))
                    .signWith(keyPair.private, algorithm)
                    .compact()

    fun invalidateToken(token: String) {
        jwtBlockListRepo.save(InvalidatedJwt(
                token = token,
                expiry = parseJwt(token).body.expiration)
        )
    }

    @Scheduled(fixedDelayString = "\${jwt.blocklist.cleanup.delay.fixed:43200000}",
            initialDelayString = "\${jwt.blocklist.cleanup.delay.initial:43200000}")
    fun cleanUpBlockList() = jwtBlockListRepo.deleteAllExpired()
}

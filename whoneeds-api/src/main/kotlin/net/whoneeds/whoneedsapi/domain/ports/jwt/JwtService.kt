package net.whoneeds.whoneedsapi.domain.ports.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
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
        private val jwtBlockListRepo: JwtBlockListRepository
) {
    private val algorithm = SignatureAlgorithm.PS256
    private val keyPair: KeyPair = Keys.keyPairFor(algorithm)
    private val expiration: Duration = Duration.ofHours(12)

    fun generateJwt(subject: String): String =
            Jwts.builder()
                    .setSubject(subject)
                    .setExpiration(Date.from(Instant.now().plus(expiration)))
                    .setIssuedAt(Date.from(Instant.now()))
                    .signWith(keyPair.private, algorithm)
                    .compact()

    fun parseJwt(jwt: String): Jws<Claims> =
            Jwts.parserBuilder()
                    .setSigningKey(keyPair.public)
                    .build()
                    .parseClaimsJws(jwt)

    @Scheduled(fixedDelay = 43200000, initialDelay = 43200000) // TODO: fix SpEL problem
    fun cleanUpBlockList() = jwtBlockListRepo.deleteAllExpired()
}

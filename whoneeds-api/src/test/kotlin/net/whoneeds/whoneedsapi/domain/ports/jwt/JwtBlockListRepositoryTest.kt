package net.whoneeds.whoneedsapi.domain.ports

import net.whoneeds.whoneedsapi.domain.model.jwt.InvalidatedJwt
import net.whoneeds.whoneedsapi.domain.model.jwt.JwtBlockListRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant
import java.util.*

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@ExtendWith(SpringExtension::class)
@DataJpaTest
internal class JwtBlockListRepositoryTest
@Autowired constructor(
        private val jwtBlockListRepository: JwtBlockListRepository
) {
    @Test
    fun `should remove all expired tokens`() {
        with(jwtBlockListRepository) {
            saveAll(mutableListOf(
                    InvalidatedJwt(token = "1", expiry = Date.from(Instant.now().plusSeconds(5))),
                    InvalidatedJwt(token = "2", expiry = Date.from(Instant.now().plusSeconds(1000))),
                    InvalidatedJwt(token = "3", expiry = Date.from(Instant.now().minusSeconds(1000))),
            ))
        }

        jwtBlockListRepository.deleteAllExpired()

        assertThat(jwtBlockListRepository.findAll().stream()
                .map(InvalidatedJwt::token))
                .doesNotContain("3")
    }
}

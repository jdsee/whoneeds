package net.whoneeds.whoneedsapi.domain.ports.jwt

import net.whoneeds.whoneedsapi.domain.model.jwt.InvalidatedJwt
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface JwtBlockListRepository : JpaRepository<InvalidatedJwt, Long> {
    fun existsByToken(token: String): Boolean

    @Transactional
    @Modifying
    @Query("DELETE FROM InvalidatedJwt jwt " +
            "WHERE CURRENT_DATE >= jwt.expiry")
    fun deleteAllExpired()
}
package net.whoneeds.whoneedsapi.domain.model.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, Long> {
    fun findByEmail(email: String): UserAccount?
}

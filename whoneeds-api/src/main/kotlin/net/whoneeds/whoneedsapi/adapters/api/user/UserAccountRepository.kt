package net.whoneeds.whoneedsapi.adapters.api.user

import net.whoneeds.whoneedsapi.domain.model.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, Long> {
    fun findByEmailAddress(emailAddress: String): UserAccount?
}

package net.whoneeds.whoneedsapi.config.security

import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class PersistenceUserDetailsService(
        private val userAccountRepository: UserAccountRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userAccountRepository.findByEmail(username)
        return user?.toSpringUser() ?: throw UsernameNotFoundException(username)
    }

    private fun UserAccount.toSpringUser() = User(email, password, emptyList())
}

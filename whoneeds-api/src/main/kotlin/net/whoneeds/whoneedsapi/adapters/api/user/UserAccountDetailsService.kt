package net.whoneeds.whoneedsapi.adapters.api.user

import net.whoneeds.whoneedsapi.domain.model.UserAccount
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class UserAccountDetailsService(
        private val userAccountRepository: UserAccountRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userAccountRepository.findByUsername(username)
        return user?.toSpringUser() ?: throw UsernameNotFoundException(username)
    }

    private fun UserAccount.toSpringUser() = User(username, password, emptyList())
}
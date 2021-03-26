package net.whoneeds.whoneedsapi.domain.ports.users

import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Service
class UserService(
        val userRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder
) {
    fun createUser(userAccount: UserAccount): UserAccount {
        userAccount.password = passwordEncoder.encode(userAccount.password)
        return userRepository.save(userAccount)
    }

    fun getActiveUser(principal: Principal): UserAccount {
        return userRepository.findByEmail(principal.name)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error: Principal '${principal.name}' can not be found")
    }

    fun getUserIfAuthorized(id: Long, principal: Principal): UserAccount {
        val user = userRepository.getOne(id)

        return if (user.email == principal.name) user
        else throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
    }

    fun getAllUsers(): MutableList<UserAccount> {
        return userRepository.findAll()
    }

    fun changePassword(mail: String, password: String) {
        val user = userRepository.findByEmail(mail)
        user?.password = passwordEncoder.encode(password)
        userRepository.save(user ?: throw KotlinNullPointerException("User is null"))
    }
}

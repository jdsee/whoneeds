package net.whoneeds.whoneedsapi.adapters.api.user

import net.whoneeds.whoneedsapi.domain.model.UserAccount
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@RestController
@RequestMapping("/users")
class UserController(
        private val userAccountRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder) {

    /**
     * Registers a new user.
     */
    @PostMapping
    fun register(@RequestBody userAccount: UserAccount) {
        userAccount.password = passwordEncoder.encode(userAccount.password)
        userAccountRepository.save(userAccount)
    }

    /**
     * Returns the actually logged in user.
     */
    @GetMapping("/me")
    fun getLoggedInUser(principal: Principal): UserAccount {
        return userAccountRepository.findByEmail(principal.name)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error: Principal '${principal.name}' can not be found")
//        return """{"user":{"id":1}}"""
    }


    /**
     * Returns the user with the specified id if known.
     * TODO: Sensitive information should only send for logged in users
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserAccount {
        return userAccountRepository.getOne(id)
    }
}
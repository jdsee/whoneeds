package net.whoneeds.whoneedsapi.adapters.api.users

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.ports.users.UserAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.security.Principal

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@RestController
@RequestMapping(USERS_ROUTE)
class UserController(
        private val userRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder) {

    /**
     * Registers a new user.
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerNewUser(@RequestBody userAccount: UserAccount): Long? {
        userAccount.password = passwordEncoder.encode(userAccount.password)
        val savedUser = userRepository.save(userAccount)
        return savedUser.id
    }

    /**
     * Returns the actually logged in user.
     */
    @GetMapping("/me")
    fun getLoggedInUser(principal: Principal): UserAccount {
        return userRepository.findByEmail(principal.name)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error: Principal '${principal.name}' can not be found")
    }

    /**
     * Returns the user with the specified id if known.
     * TODO: Sensitive information should only send for logged in users
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserAccount {
        return userRepository.getOne(id)
    }

    /**
     * TODO: This should be forbidden for all roles but admin as soon as roles are implemented
     */
    @GetMapping
    fun getAllUsers(principal: Principal): MutableList<UserAccount> {
        return userRepository.findAll()
    }
}
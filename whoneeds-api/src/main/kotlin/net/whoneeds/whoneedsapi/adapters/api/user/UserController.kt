package net.whoneeds.whoneedsapi.adapters.api.user

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.UserAccount
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
        private val userAccountRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder) {

    /**
     * Registers a new user.
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerNewUser(@RequestBody userAccount: UserAccount): Long? {
        userAccount.password = passwordEncoder.encode(userAccount.password)
        val savedUser = userAccountRepository.save(userAccount)
        return savedUser.id
    }

    /**
     * Returns the actually logged in user.
     */
    @GetMapping("/me")
    fun getLoggedInUser(principal: Principal): UserAccount {
        return userAccountRepository.findByEmail(principal.name)
                ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Server Error: Principal '${principal.name}' can not be found")
    }

    /**
     * Returns the user with the specified id if known.
     * TODO: Sensitive information should only send for logged in users
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserAccount {
        return userAccountRepository.getOne(id)
    }

    /**
     * TODO: This should be forbidden for all roles but admin as soon as roles are implemented
     */
    @GetMapping
    fun getAllUsers(principal: Principal): MutableList<UserAccount> {
        return userAccountRepository.findAll()
    }
}
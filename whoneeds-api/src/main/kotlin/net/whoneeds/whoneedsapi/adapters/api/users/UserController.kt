package net.whoneeds.whoneedsapi.adapters.api.users

import net.whoneeds.whoneedsapi.RoutingEndpointConstants.USERS_ROUTE
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import net.whoneeds.whoneedsapi.domain.model.users.UserAccountRepository
import net.whoneeds.whoneedsapi.domain.ports.users.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.security.Principal
import javax.annotation.security.RolesAllowed

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@RestController
@RequestMapping(USERS_ROUTE)
class UserController(
        private val userService: UserService,
        private val userRepository: UserAccountRepository,
        private val passwordEncoder: PasswordEncoder
) {

    /**
     * Registers a new user and returns the new location
     * in the response Location-Header.
     */
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerNewUser(
            @RequestBody userAccount: UserAccount,
            uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Void> {
        val id = userService.createUser(userAccount).id
        val location = uriBuilder.path("$USERS_ROUTE/{id}").buildAndExpand(id)
        return ResponseEntity.created(location.toUri()).build()
    }

    /**
     * Returns the actually logged in user.
     */
    @GetMapping("/me")
    fun getActiveUser(principal: Principal): UserAccount {
        return userService.getActiveUser(principal)
    }

    /**
     * Returns the user with the specified id if known.
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long,
                principal: Principal): UserAccount {
        // TODO: Sensitive information should only send with authorization

        return userService.getUserIfAuthorized(id, principal)
    }

    /**
     * Returns all registered users
     */
    @GetMapping
    @RolesAllowed("CURRENTLY_BLOCKED")
    fun getAllUsers(principal: Principal): MutableList<UserAccount> {
        // TODO: This should be forbidden for all roles but admin as soon as roles are implemented

        return userService.getAllUsers()
    }    /**
     * Changes the user password.
     */
    @PutMapping("/{changePassword}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun changePassword(@RequestBody credentials: Credentials) {
        val user = userRepository.findByEmail(credentials.email)
        user?.password = passwordEncoder.encode(credentials.newPassword)
        userRepository.save(user ?: throw KotlinNullPointerException("User is null"))
    }

    // TODO: PUT
    // TODO: DELETE
}
data class Credentials(val email: String, val newPassword: String)

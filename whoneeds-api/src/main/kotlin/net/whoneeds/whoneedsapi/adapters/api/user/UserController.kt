package net.whoneeds.whoneedsapi.adapters.api.user

import net.whoneeds.whoneedsapi.domain.model.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@RestController
class UserController(userAccountRepository: UserAccountRepository,
                     passwordEncoder: PasswordEncoder) {
    @PostMapping("/register")
    fun register(@RequestBody userAccount: UserAccount) {

    }
}
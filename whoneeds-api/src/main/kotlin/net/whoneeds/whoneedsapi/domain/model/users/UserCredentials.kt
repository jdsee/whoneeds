package net.whoneeds.whoneedsapi.domain.model.users

import javax.validation.constraints.Email
import javax.validation.constraints.Min

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
data class UserCredentials(
        @Email
        val email: String,
        @Min(8)
        val password: String
)

package net.whoneeds.whoneedsapi.domain.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserAccount(
        @Id
        @GeneratedValue
        var id: Long,
        var username: String,
        var password: String
)

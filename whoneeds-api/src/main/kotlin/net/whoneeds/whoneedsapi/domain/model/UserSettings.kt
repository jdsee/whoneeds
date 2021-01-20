package net.whoneeds.whoneedsapi.domain.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UserSettings(
        @GeneratedValue
        @Id
        var id: Long,
        var locale: String
)
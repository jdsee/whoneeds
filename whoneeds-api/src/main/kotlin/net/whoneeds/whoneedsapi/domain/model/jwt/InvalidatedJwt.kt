package net.whoneeds.whoneedsapi.domain.model.jwt


import java.util.*
import javax.persistence.*

/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Entity
data class InvalidatedJwt(
        @Id
        @GeneratedValue
        private var id: Long? = null,
        @Column(length = 512)
        var token: String,
        @Temporal(TemporalType.DATE)
        private var expiry: Date
)

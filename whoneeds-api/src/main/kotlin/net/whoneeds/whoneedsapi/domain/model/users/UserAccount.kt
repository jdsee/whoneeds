package net.whoneeds.whoneedsapi.domain.model.users

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.Min

@Entity
data class UserAccount(
        @Id
        @GeneratedValue
        var id: Long? = null,
        @JsonProperty(access = Access.WRITE_ONLY)
        @Min(6)
        var password: String,
        @Email
        @Column(unique = true)
        var email: String,
        @JsonInclude(Include.NON_NULL)
        var username: String? = null, // for now email will be used for login
)

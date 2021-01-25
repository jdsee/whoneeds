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
import javax.validation.constraints.NotBlank

@Entity
data class UserAccount(
        @Id
        @GeneratedValue
        var id: Long? = null,
        @Email
        @Column(unique = true)
        var email: String,
        @JsonProperty(access = Access.WRITE_ONLY)
        @Min(8)
        var password: String,
        @JsonInclude(Include.NON_NULL)
        @NotBlank
        var name: String,
        @NotBlank
        var surname: String
)

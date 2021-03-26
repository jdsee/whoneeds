package net.whoneeds.whoneedsapi.domain.model.users

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonProperty.Access
import net.whoneeds.whoneedsapi.domain.model.projects.Project
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
data class UserAccount(
        @Email
        @Column(unique = true)
        var email: String,
        @JsonProperty(access = Access.WRITE_ONLY)
        @Min(8)
        var password: String,
        @NotBlank
        var name: String,
        @NotBlank
        var surname: String,
        @ManyToMany(mappedBy = "members")
        var projects: MutableSet<Project> = mutableSetOf(),
        @Id
        @GeneratedValue
        @JsonInclude(Include.NON_NULL)
        var id: Long? = null
)

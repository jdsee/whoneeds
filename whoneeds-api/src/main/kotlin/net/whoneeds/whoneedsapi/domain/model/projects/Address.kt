package net.whoneeds.whoneedsapi.domain.model.projects

import org.hibernate.validator.constraints.Length
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
data class Address(
        @NotEmpty
        var name: String,
        @NotEmpty
        var street: String,
        @NotNull
        var nr: Int,
        @NotEmpty
        var city: String,
        @Length(min = 5, max = 5)
        var zip: Int,
        var state: String? = null,
        @Id
        @GeneratedValue
        var id: Long? = null
)

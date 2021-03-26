package net.whoneeds.whoneedsapi.domain.model.projects

import com.fasterxml.jackson.annotation.*
import net.whoneeds.whoneedsapi.domain.model.users.UserAccount
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


/**
@author Joscha Seelig <jduesentrieb> 2021
 **/
@Entity
data class Project(
    @NotBlank
    @Column(unique = true)
    var name: String,
    @NotBlank
    var description: String,
    @ManyToOne
    @JoinColumn(name = "creator_id")
    var creator: UserAccount?,
    @ManyToMany
    @JoinTable(
        name = "project_members",
        joinColumns = [JoinColumn(name = "project_id")],
        inverseJoinColumns = [JoinColumn(name = "member_id")]
    )
    var members: MutableSet<UserAccount>,
    @NotNull
    @ManyToOne(cascade = [CascadeType.ALL])
    var address: Address,
    @ElementCollection
    var categories: MutableSet<SupportCategory> = mutableSetOf(),
    @Id
    @GeneratedValue
    var id: Long? = null

    // TODO: add demanded goods
)

enum class SupportCategory {
    @JsonProperty("humanitarian")
    HUMANITARIAN_AID,

    @JsonProperty("refugees")
    REFUGEE_AID,

    @JsonProperty("homeless")
    HOMELESS_ASSISTANCE,

    @JsonProperty("educational")
    EDUCATIONAL_SUPPORT,

    @JsonProperty("medical")
    MEDICAL_HELP,

    @JsonProperty("children")
    CHILD_AID,

    @JsonProperty("food")
    FOOD_DISTRIBUTION,

    @JsonProperty("environmental")
    ENVIRONMENTAL_PROTECTION,

    @JsonProperty("other")
    OTHER
}

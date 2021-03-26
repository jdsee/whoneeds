package net.whoneeds.whoneedsapi.adapters.api.mail

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
data class EmailRequest(
        val subject: String = "",
        val targetEmail: String = "",
        val text: String = "",
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val name: String = ""
)

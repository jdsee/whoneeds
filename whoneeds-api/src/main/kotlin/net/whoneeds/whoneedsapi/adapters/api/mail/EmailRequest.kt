package net.whoneeds.whoneedsapi.adapters.api.mail

/**
 * @author Lukas Schuetz <pomcom> 2021
 */
data class EmailRequest(
        val subject: String = "",
        val targetEmail: String = "",
        val text: String = "",
        val name: String = ""
)

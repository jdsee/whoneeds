package net.whoneeds.whoneedsapi

object RoutingEndpointConstants {
    const val LOGIN_ROUTE = "/login"
    const val USERS_ROUTE = "/users"
    const val RESET_PASSWORD_ROUTE = "/resetPassword"
}

object SecurityConstants {
    const val BEARER_TOKEN_PREFIX = "Bearer"
    const val UNAUTHORIZED_REALM = "realm=\"whoneeds-api-realm\""
}

object EmailSenderConstants {
    const val SEND_MAIL = "/email"
    const val SEND_TEMPLATE = "/email/template"
    const val SEND_WITH_ATTACHMENT = "/email/attachment"
}

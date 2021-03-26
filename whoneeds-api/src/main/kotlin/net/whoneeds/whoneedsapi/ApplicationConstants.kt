package net.whoneeds.whoneedsapi

object RoutingEndpointConstants {
    const val LOGIN_ROUTE = "/login"
    const val LOGOUT_ROUTE = "/logout"
    const val USERS_ROUTE = "/users"
    const val PROJECTS_ROUTE = "/projects"
    const val RESET_PASSWORD_ROUTE = "/resetPassword"
    const val SEND_MAIL = "/email"
    const val SEND_TEMPLATE = "/email/template"
    const val SEND_WITH_ATTACHMENT = "/email/attachment"
}

object SecurityConstants {
    const val BEARER_TOKEN_PREFIX = "Bearer"
    const val UNAUTHORIZED_REALM = "realm=\"whoneeds-api-realm\""
}

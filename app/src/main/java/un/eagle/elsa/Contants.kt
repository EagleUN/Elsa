package un.eagle.elsa

object Constants {
    const val APP_PACKAGE = "un.eagle.elsa"
    const val GRAPHQL_URL = "http://35.209.23.230:8081/graphql"
    const val CHANNEL_ID = "un.eagle.elsa.notifications";
    const val MIN_PASSWORD_LENGTH = 8

    object Preferences {
        const val USER_ID = "user_id"
        const val JWT_TOKEN = "jwt_token"
    }

    object Api {
        object Headers {
            const val AUTH = "Authorization"
        }
    }
}
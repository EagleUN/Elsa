package un.eagle.elsa.data.model

data class Notification (
    val sourceUserName: String,
    val notificationType : Int
)
{

    fun isShare() : Boolean { return notificationType == SHARE }
    fun isFollow() : Boolean { return notificationType == FOLLOW }

    companion object {
        const val FOLLOW = 0
        const val SHARE = 1
    }
}
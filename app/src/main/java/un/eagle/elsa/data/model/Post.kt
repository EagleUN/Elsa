package un.eagle.elsa.data.model


data class Post(
    val id : String,
    val authorId : String,
    val authorName : String,
    val createdAt : String,
    val content : String,
    val sharedBy : String? = null
)
{
    fun isPost() : Boolean { return sharedBy == null }
    fun isShare() : Boolean { return  sharedBy != null }
}
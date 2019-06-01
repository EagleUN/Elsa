package un.eagle.elsa.data.model


data class Post(
    val author : String,
    val content : String,
    val sharedBy : String? = null
)
{
    fun isPost() : Boolean { return sharedBy == null }
    fun isShare() : Boolean { return  sharedBy != null }
}
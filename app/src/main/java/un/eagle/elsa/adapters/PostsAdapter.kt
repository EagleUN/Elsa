package un.eagle.elsa.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.data.model.Post

class PostsAdapter(private val posts: ArrayList<Post>) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun getItemCount(): Int { return posts.size }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): PostViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_post, viewGroup, false)
        return PostViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: PostViewHolder, index: Int) {
        val post: Post = posts[index]
        viewHolder.textViewAuthor.text  = post.author
        viewHolder.textViewContent.text = post.content
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textViewAuthor  = v.findViewById(R.id.postAuthor)  as TextView
        val textViewContent = v.findViewById(R.id.postContent) as TextView
    }
}
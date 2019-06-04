package un.eagle.elsa.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.data.model.Post


class PostsAdapter(private val posts: ArrayList<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int { return posts.size }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    {
        if ( viewType == 1 )
        {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_share, viewGroup, false)
            return ShareViewHolder(v)
        }
        else
        {
            val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_post, viewGroup, false)
            return PostViewHolder(v)
        }
    }

    override fun getItemViewType(index: Int): Int {
        return if ( posts[index].isShare() ) 1 else 0
    }


    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, index: Int) {
        val post: Post = posts[index]
        if ( viewHolder.itemViewType == 1 )
        {
            viewHolder as ShareViewHolder
            val res = viewHolder.authorTextView.resources
            viewHolder.authorTextView.text = post.author
            viewHolder.contentTextView.text = post.content
            viewHolder.sharedByTextView.text = String.format(res.getString(R.string.user_shares), post.sharedBy)
        }
        else
        {
            viewHolder as PostViewHolder
            viewHolder.authorTextView.text = post.author
            viewHolder.contentTextView.text = post.content
        }
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val authorTextView   = v.findViewById(R.id.postAuthor)  as TextView
        val contentTextView  = v.findViewById(R.id.postContent) as TextView
    }

    class ShareViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val authorTextView   = v.findViewById(R.id.postAuthor)  as TextView
        val contentTextView  = v.findViewById(R.id.postContent) as TextView
        val sharedByTextView = v.findViewById(R.id.itemShare_sharedBy) as TextView
    }
}
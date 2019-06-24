package un.eagle.elsa.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.CreateShareMutation
import un.eagle.elsa.R
import un.eagle.elsa.data.model.Post
import un.eagle.elsa.graphql.Client


class PostsAdapter(
    private val posts: ArrayList<Post>,
    private val userId: String,
    private val type: Int,
    private val activity: Activity
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TAG = "Eagle.PostsAdapter"
        const val HOME = 0
        const val PROFILE = 1
    }

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
            viewHolder.authorTextView.text = post.authorId
            viewHolder.contentTextView.text = post.content
            viewHolder.sharedByTextView.text = String.format(res.getString(R.string.user_shares), post.sharedBy)
        }
        else
        {
            viewHolder as PostViewHolder
            Log.d(TAG, "userId = $userId, id=${post.id}")
            if ( userId.equals(post.authorId) || type == PROFILE)
            {
                viewHolder.shareButton.visibility = View.GONE
            }
            else
            {
                viewHolder.shareButton.visibility = View.VISIBLE
                val callback = object : ApolloCall.Callback<CreateShareMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d(TAG, "Failed to share post")
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity,
                                activity.resources.getString(R.string.action_share_fail),
                                Toast.LENGTH_SHORT
                                ).show()
                        }
                    }

                    override fun onResponse(response: Response<CreateShareMutation.Data>) {
                        Log.d(TAG, "Successfully shared post")
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity,
                                activity.resources.getString(R.string.action_share_successful),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                viewHolder.shareButton.setOnClickListener{
                    Client.createShare(userId, post.id, callback)
                }
            }
            viewHolder.authorTextView.text = post.authorId
            viewHolder.contentTextView.text = post.content
        }
    }

    class PostViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val authorTextView   = v.findViewById(R.id.postAuthor)  as TextView
        val contentTextView  = v.findViewById(R.id.postContent) as TextView
        val shareButton = v.findViewById(R.id.itemPost_share) as Button
    }

    class ShareViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val authorTextView   = v.findViewById(R.id.postAuthor)  as TextView
        val contentTextView  = v.findViewById(R.id.postContent) as TextView
        val sharedByTextView = v.findViewById(R.id.itemShare_sharedBy) as TextView
    }
}
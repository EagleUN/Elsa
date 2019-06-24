package un.eagle.elsa.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.data.model.OtherUser
import android.content.Intent
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import un.eagle.elsa.activities.ShowUserActivity


class OtherUserListAdapter(private val mContext : Context, private val otherUsers : ArrayList<OtherUser>)
    : RecyclerView.Adapter<OtherUserListAdapter.OtherUserViewHolder>()
{
    companion object {
        const val TAG = "Eagle.OtherUserListAdap"
    }

    override fun getItemCount(): Int {  return otherUsers.size  }

    override fun onBindViewHolder(viewHolder: OtherUserViewHolder, index: Int) {
        val u = otherUsers[index]
        val res = viewHolder.iFollowTextView.resources

        viewHolder.nameTextView.text = u.name + " " + u.lastName
        val followsMeStrId = if ( u.followsMe ) R.string.follows_me else R.string.does_not_follow_me
        viewHolder.followsMeTextView.text = res.getString(followsMeStrId)

        if ( u.iFollow ) {
            viewHolder.iFollowTextView.text = res.getString(R.string.i_am_following)
        }
        else {
            viewHolder.iFollowTextView.text = res.getString(R.string.i_am_not_following)
        }

        viewHolder.parentLayout.setOnClickListener {
            val intent = Intent(mContext, ShowUserActivity::class.java)
            intent.putExtra(ShowUserActivity.USER_ID, u.id)
            intent.putExtra(ShowUserActivity.USER_NAME, u.name)
            intent.putExtra(ShowUserActivity.USER_LAST_NAME, u.lastName)
            intent.putExtra(ShowUserActivity.USER_I_FOLLOW, u.iFollow)
            intent.putExtra(ShowUserActivity.USER_FOLLOWS_ME, u.followsMe)
            mContext.startActivity(intent)

        }

        /*viewHolder.iFollowTextView.setOnClickListener {
            if ( u.iFollow )
            {
                val callback = object : ApolloCall.Callback<DeleteFollowMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d(TAG, "Could not delete follow $loggedUserId -> ${u.id}")
                    }

                    override fun onResponse(response: Response<DeleteFollowMutation.Data>) {
                        otherUsers[index] = u.negateIFollow()
                        notifyItemRangeChanged(index, 1)
                    }
                }
                Client.deleteFollow(loggedUserId, u.id, callback)
            }
            else
            {
                val callback = object : ApolloCall.Callback<CreateFollowMutation.Data>() {
                    override fun onFailure(e: ApolloException) {
                        Log.d(TAG, "Could not create follow $loggedUserId -> ${u.id}")
                    }

                    override fun onResponse(response: Response<CreateFollowMutation.Data>) {
                        otherUsers[index] = u.negateIFollow()
                        notifyItemRangeChanged(index, 1)
                    }
                }
                Client.createFollow(loggedUserId, u.id, callback)
            }
        }*/
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): OtherUserViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_other_user, viewGroup, false)
        return OtherUserViewHolder(v)
    }

    class OtherUserViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        val parentLayout = v.findViewById(R.id.item_otherUser_parentLayout) as CardView
        val nameTextView = v.findViewById(R.id.item_otherUser_name) as TextView
        val followsMeTextView = v.findViewById(R.id.item_otherUser_followsMe) as TextView
        val iFollowTextView = v.findViewById(R.id.item_otherUser_iFollow) as TextView
    }
}
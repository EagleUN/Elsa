package un.eagle.elsa.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.data.model.OtherUser

class OtherUserListAdapter(private val otherUsers : List<OtherUser>)
    : RecyclerView.Adapter<OtherUserListAdapter.OtherUserViewHolder>()
{
    companion object {
        const val TAG = "Eagle.OtherUserListAdap"
    }

    override fun getItemCount(): Int {  return otherUsers.size  }

    override fun onBindViewHolder(viewHolder: OtherUserViewHolder, index: Int) {
        val u = otherUsers[index]
        viewHolder.nameTextView.text = u.name

        val followsMeStrId = if ( u.followsMe ) R.string.follows_me else R.string.does_not_follow_me
        viewHolder.followsMeTextView.text = viewHolder.followsMeTextView.resources.getString(followsMeStrId)

        viewHolder.followButton.isActivated = !u.iFollow

        viewHolder.followButton.setOnClickListener { followUser(u.id) }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): OtherUserViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_other_user, viewGroup, false)
        return OtherUserViewHolder(v)
    }


    private fun followUser(userId: String) {
        Log.d(TAG, "Follow user $userId")
    }

    class OtherUserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameTextView = v.findViewById(R.id.item_otherUser_name) as TextView
        val followsMeTextView = v.findViewById(R.id.item_otherUser_followsMe) as TextView
        val followButton = v.findViewById(R.id.item_otherUser_followButton) as Button
    }
}
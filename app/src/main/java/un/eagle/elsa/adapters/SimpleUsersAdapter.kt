package un.eagle.elsa.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import un.eagle.elsa.R

class SimpleUsersAdapter(private val users : List<String>) : RecyclerView.Adapter<SimpleUsersAdapter.UserViewHolder>() {

    override fun getItemCount(): Int { return users.size }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_simple_user, viewGroup, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: UserViewHolder, index: Int) {
        viewHolder.nameTextView.text = users[index]
    }

    class UserViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameTextView = v.findViewById(R.id.userItem_userName_textView) as TextView;
    }
}
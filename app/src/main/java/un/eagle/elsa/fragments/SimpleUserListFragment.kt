package un.eagle.elsa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import un.eagle.elsa.MockData
import un.eagle.elsa.R
import un.eagle.elsa.adapters.SimpleUsersAdapter

class SimpleUserListFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.SimpleUserListFragment"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_simple_user_list,null)

        val postsView : RecyclerView = v.findViewById(R.id.simpleUserListRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val data = MockData.userList()

        postsView.adapter = SimpleUsersAdapter(data)

        return v
    }
}
package un.eagle.elsa.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.R
import un.eagle.elsa.UserListQuery
import un.eagle.elsa.adapters.OtherUserListAdapter
import un.eagle.elsa.data.model.OtherUser
import un.eagle.elsa.graphql.Client


class OtherUserListFragment : Fragment()
{
    companion object {
        const val TAG = "Eagle.SimpleUserListFra"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View =  inflater.inflate(R.layout.fragment_other_user_list,null)

        val postsView = v.findViewById(R.id.otherUserListRecyclerView) as RecyclerView
        postsView!!.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val activity = activity!!
        val userId = ElsaPreferences.getUserId(activity)

        val callback = object : ApolloCall.Callback<UserListQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Failed to get userList for userId=$userId")
            }

            override fun onResponse(response: Response<UserListQuery.Data>) {
                Log.d(TAG, "Successfully got userList for userId=$userId")
                val responseList = response.data()?.userList()?.otherUsers()
                val data = ArrayList<OtherUser>()
                responseList?.forEach {
                    val otherUser = OtherUser(
                        id = it.id(),
                        name = it.name(),
                        lastName =  it.lastName(),
                        followsMe = it.followsMe(),
                        iFollow = it.iFollow() )
                    Log.d(TAG, "Other user: $otherUser")
                    data.add(
                        otherUser
                    )
                }
                activity.runOnUiThread {
                    postsView.adapter = OtherUserListAdapter(data)
                }


            }
        }

        Client.getUserListFor(userId, callback)

        return v
    }
}

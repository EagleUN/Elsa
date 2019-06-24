package un.eagle.elsa.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.*
import un.eagle.elsa.adapters.SimpleUsersAdapter
import un.eagle.elsa.graphql.Client

class SimpleUserListFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.SimpleUserListFra"

        const val TYPE = "ListType"

        const val FOLLOWERS = 0

        const val FOLLOWING = 1
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_simple_user_list,null)

        val postsView : RecyclerView = v.findViewById(R.id.simpleUserListRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val type = arguments?.getInt(TYPE)
        val activity = activity!!
        val userId = ElsaPreferences.getUserId(activity)


        if ( type == FOLLOWERS )
        {
            val callback = object : ApolloCall.Callback<FollowersQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "Could not load following of ${userId}")
                }

                override fun onResponse(response: Response<FollowersQuery.Data>) {
                    Log.d(TAG, "Successfully loaded following of ${userId}")
                    val resData = response.data()?.followers()?.userIds()!!
                    val data = ArrayList<String>()
                    resData.forEach{ data.add(it) }
                    activity.runOnUiThread{
                        postsView.adapter = SimpleUsersAdapter(resData)
                    }

                }
            }
            Client.queryFollowersFor(userId, callback)
        }
        else if ( type == FOLLOWING )
        {
            val callback = object : ApolloCall.Callback<FollowingQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "Could not load followers of ${userId}")
                }

                override fun onResponse(response: Response<FollowingQuery.Data>) {
                    Log.d(TAG, "Successfully loaded followers of ${userId}")
                    val resData = response.data()?.following()?.userIds()!!
                    val data = ArrayList<String>()
                    resData.forEach{ data.add(it) }
                    activity.runOnUiThread{
                        postsView.adapter = SimpleUsersAdapter(resData)
                    }

                }
            }
            Client.queryFollowingFor(userId, callback)
        }


        return v
    }
}
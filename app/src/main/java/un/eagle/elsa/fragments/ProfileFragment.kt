package un.eagle.elsa.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.QueryFollowers
import un.eagle.elsa.QueryFollowing
import un.eagle.elsa.R
import un.eagle.elsa.activities.FollowersActivity
import un.eagle.elsa.activities.FollowingActivity
import un.eagle.elsa.graphql.Client

class ProfileFragment : Fragment() {

    private fun goToFollowersActivity() {
        activity?.let {
            val intent = Intent (it, FollowersActivity::class.java)
            it.startActivity(intent)
        }
    }

    private fun goToFollowingActivity() {
        activity?.let {
            val intent = Intent (it, FollowingActivity::class.java)
            it.startActivity(intent)
        }
    }

    private fun goToUpdateDataActivity() {
        activity?.let {
            //TODO
        }
    }

    private fun logOut() {
        //TODO
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = getString(R.string.title_profile)
        val v : View = inflater.inflate(R.layout.fragment_profile,null)

        val updateDataButton : Button   = v.findViewById(R.id.profile_updateData_button)
        val logOutButton     : Button   = v.findViewById(R.id.profile_logOut_button)
        //val nameTV           : TextView = v.findViewById(R.id.profile_name_textView)
        //val emailTV          : TextView = v.findViewById(R.id.profile_email_textView)
        val followersTV      : TextView = v.findViewById(R.id.profile_followers_textView)
        val followingTV      : TextView = v.findViewById(R.id.profile_following_textView)
        followersTV.text = ""
        followingTV.text = ""

        logOutButton.setOnClickListener { logOut() }
        updateDataButton.setOnClickListener { goToUpdateDataActivity() }
        followersTV.setOnClickListener { goToFollowersActivity() } //TODO
        followingTV.setOnClickListener { goToFollowingActivity() } //TODO

        val activity = activity!!
        val userId = ElsaPreferences.getUserId(activity)


        val callbackFollowers = object : ApolloCall.Callback<QueryFollowers.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(SimpleUserListFragment.TAG, "Could not load following of ${userId}")
            }

            override fun onResponse(response: Response<QueryFollowers.Data>) {
                Log.d(SimpleUserListFragment.TAG, "Successfully loaded following of ${userId}")
                val count = response.data()?.followers()?.count()!!
                activity.runOnUiThread{
                    followersTV.text = count.toString()
                }

            }
        }


        val callbackFollowing = object : ApolloCall.Callback<QueryFollowing.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(SimpleUserListFragment.TAG, "Could not load followers of ${userId}")
            }

            override fun onResponse(response: Response<QueryFollowing.Data>) {
                Log.d(SimpleUserListFragment.TAG, "Successfully loaded followers of ${userId}")
                val count = response.data()?.following()?.count()!!
                activity.runOnUiThread{
                    followingTV.text = count.toString()
                }
            }
        }

        Client.getFollowersFor(userId, callbackFollowers)
        Client.getFollowingFor(userId, callbackFollowing)


        return v
    }
}
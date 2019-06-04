package un.eagle.elsa.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.*
import un.eagle.elsa.fragments.ProfileFragment
import un.eagle.elsa.graphql.Client

class ShowUserActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.ShowUserActivity"


        const val USER_ID         = "Eagle.ID"
        const val USER_NAME       = "Eagle.UserName"
        const val USER_LAST_NAME  = "Eagle.LastName"
        const val USER_I_FOLLOW   = "Eagle.IFollow"
        const val USER_FOLLOWS_ME = "Eagle.FollowsMe"
    }

    private var iFollow = false
    private var followButton : Button? = null
    private var followersTV : TextView? = null
    private var followingTV: TextView? = null

    private val myLock = Any()

    fun setIFollow ( flag : Boolean ) {
        synchronized(myLock) {
            if ( iFollow != flag ) {
                val cur = followersTV?.text.toString().toInt()
                if ( flag )
                    followersTV?.text = (cur+1).toString()
                else
                    followersTV?.text = (cur-1).toString()
            }
            iFollow = flag
            followButton?.text = resources.getString (
                if ( iFollow ) R.string.action_unfollow
                else R.string.action_follow )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_user)

        val userId = ElsaPreferences.getUserId(this)
        val otherUserId = intent.getStringExtra(USER_ID)
        val otherUserName = intent.getStringExtra(USER_NAME)
        val otherUserLastName = intent.getStringExtra(USER_LAST_NAME)
        iFollow = intent.getBooleanExtra(USER_I_FOLLOW, false)
        val followsMe = intent.getBooleanExtra(USER_FOLLOWS_ME, false)
        title = otherUserName

        followButton = findViewById<Button>(R.id.showUser_follow)
        val nameTV           : TextView = findViewById(R.id.showUser_name_textView)
        val lastNameTV       : TextView = findViewById(R.id.showUser_lastName_textView)
        followersTV = findViewById<TextView>(R.id.showUser_followers_textView)
        followingTV = findViewById<TextView>(R.id.showUser_following_textView)
        val followsMeTV      : TextView = findViewById(R.id.showUser_followsMe)

        followersTV?.text = ""
        followingTV?.text = ""
        nameTV.text = otherUserName
        lastNameTV.text = otherUserLastName

        followsMeTV.text = resources.getString (
            if ( followsMe ) R.string.follows_me
            else R.string.does_not_follow_me )



        val activity = this

        val callbackFollowers = object : ApolloCall.Callback<QueryFollowers.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(ProfileFragment.TAG, "Could not load following of ${otherUserId}")
            }

            override fun onResponse(response: Response<QueryFollowers.Data>) {
                Log.d(ProfileFragment.TAG, "Successfully loaded following of ${otherUserId}")
                val count = response.data()?.followers()?.count()!!
                activity.runOnUiThread{
                    followersTV?.text = count.toString()
                }

            }
        }


        val callbackFollowing = object : ApolloCall.Callback<QueryFollowing.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(ProfileFragment.TAG, "Could not load followers of ${otherUserId}")
            }

            override fun onResponse(response: Response<QueryFollowing.Data>) {
                Log.d(ProfileFragment.TAG, "Successfully loaded followers of ${otherUserId}")
                val count = response.data()?.following()?.count()!!
                activity.runOnUiThread{
                    followingTV?.text = count.toString()
                }
            }
        }

        val callbackCreateFollow = object : ApolloCall.Callback<CreateFollowMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Could not delete follow :C")
            }

            override fun onResponse(response: Response<CreateFollowMutation.Data>) {
                Log.d(TAG, "Deleted follow successfully :D")
                activity.runOnUiThread{ setIFollow(false) }
            }
        }

        val callbackDeleteFollow = object : ApolloCall.Callback<DeleteFollowMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Could not create follow :C")
            }

            override fun onResponse(response: Response<DeleteFollowMutation.Data>) {
                Log.d(TAG, "Created follow successfully :D")
                activity.runOnUiThread{ setIFollow(true) }
            }
        }

        Client.queryFollowersFor(otherUserId, callbackFollowers)
        Client.queryFollowingFor(otherUserId, callbackFollowing)
        followButton?.setOnClickListener{
            if ( iFollow ) Client.createFollow(userId, otherUserId, callbackCreateFollow)
            else Client.deleteFollow(userId, otherUserId, callbackDeleteFollow)
        }
    }
}

package un.eagle.elsa.fragments

import android.content.Intent
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.HomeFeedForUserQuery
import un.eagle.elsa.ProfileFeedForUserQuery
import un.eagle.elsa.R
import un.eagle.elsa.activities.WritePostActivity
import un.eagle.elsa.adapters.PostsAdapter
import un.eagle.elsa.data.model.Post
import un.eagle.elsa.graphql.Client

class PostsFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.PostsFragment"

        const val TYPE = "FeedType"

        const val HOME = 0

        const val PROFILE = 1
    }

    /**
     * Goes to a write post activity
     */
    private fun goToWritePostActivity() {
        activity?.let{
            val intent = Intent (it, WritePostActivity::class.java)
            it.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View  =  inflater.inflate(R.layout.fragment_posts,null)
        activity?.title = getString(R.string.title_home)

        val postsView : RecyclerView = v.findViewById(R.id.postsRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val activity = activity!!
        val userId = ElsaPreferences.getUserId(activity)

        val type = arguments?.getInt(TYPE)

        if ( type == HOME )
        {
            val callbackHome = object : ApolloCall.Callback<HomeFeedForUserQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "Could not get home feed for user $userId")
                }

                override fun onResponse(response: Response<HomeFeedForUserQuery.Data>) {
                    Log.d(TAG, "Successfully got home feed for user $userId")

                    val homeFeedResponse = response.data()?.homeFeedForUser()!!
                    val data = ArrayList<Post>()
                    homeFeedResponse.forEach {
                        val p = Post(
                            id = it.id(),
                            authorId = it.idCreator(),
                            createdAt = it.createdAt(),
                            content = it.content(),
                            sharedBy = null,
                            authorName = "No have" //TODO
                        )
                        data.add(p)
                    }

                    activity.runOnUiThread {
                        val postsAdapter = PostsAdapter(data, userId, PostsAdapter.HOME, activity)
                        postsView.adapter = postsAdapter

                    }
                }
            }
            Client.getHomeFeedFor(userId, callbackHome)

            val writePostButton : FloatingActionButton = v.findViewById(R.id.writePostButton)
            writePostButton.setOnClickListener{ goToWritePostActivity() }
            writePostButton.visibility = View.VISIBLE
        }
        else if ( type == PROFILE )
        {
            val callbackProfile = object : ApolloCall.Callback<ProfileFeedForUserQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Log.d(TAG, "Could not get profile feed for user $userId")
                }

                override fun onResponse(response: Response<ProfileFeedForUserQuery.Data>) {
                    Log.d(TAG, "Successfully got profile feed for user $userId")

                    val homeFeedResponse = response.data()?.profileFeedForUser()!!
                    val data = ArrayList<Post>()
                    homeFeedResponse.forEach {
                        val p = Post(
                            id = it.id(),
                            authorId = it.idCreator(),
                            createdAt = it.createdAt(),
                            content = it.content(),
                            sharedBy = null,
                            authorName = "No have" //TODO
                        )
                        data.add(p)
                    }

                    activity.runOnUiThread {
                        val postsAdapter = PostsAdapter(data, userId, PostsAdapter.PROFILE, activity)
                        postsView.adapter = postsAdapter
                    }
                }
            }
            Client.getProfileFeedFor(userId, callbackProfile)

        }

        return v
    }
}
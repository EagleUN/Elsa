package un.eagle.elsa.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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
import un.eagle.elsa.QueryHomeFeedForUser
import un.eagle.elsa.R
import un.eagle.elsa.activities.WritePostActivity
import un.eagle.elsa.adapters.PostsAdapter
import un.eagle.elsa.data.model.Post
import un.eagle.elsa.graphql.Client

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.HomeFragment"
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
        val v : View  =  inflater.inflate(R.layout.fragment_home,null)
        activity?.title = getString(R.string.title_home)

        val postsView : RecyclerView = v.findViewById(R.id.postsRecyclerView)
        postsView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val writePostButton : FloatingActionButton = v.findViewById(R.id.writePostButton)
        val activity = activity!!
        val userId = ElsaPreferences.getUserId(activity)

        val callback = object : ApolloCall.Callback<QueryHomeFeedForUser.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Could not get home feed for user $userId")
            }

            override fun onResponse(response: Response<QueryHomeFeedForUser.Data>) {
                Log.d(TAG, "Successfully got home feed for user $userId" )

                val homeFeedResponse = response.data()?.homeFeedForUser()!!
                val data = ArrayList<Post>()
                homeFeedResponse.forEach{
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

                activity.runOnUiThread{
                    val postsAdapter = PostsAdapter(data)
                    postsView.adapter = postsAdapter

                }
            }
        }

        Client.getHomeFeedFor(userId, callback)

        writePostButton.setOnClickListener{ goToWritePostActivity() }

        return v
    }
}
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
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.NotificationsForUserQuery
import un.eagle.elsa.R
import un.eagle.elsa.adapters.NotificationsAdapter
import un.eagle.elsa.data.model.Notification
import un.eagle.elsa.graphql.Client

class NotificationsFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.NotificationsFrag"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val activity = activity!!
        activity.title = getString(R.string.title_notifications)

        val v = inflater.inflate(R.layout.fragment_notifications,null)


        val recyclerView : RecyclerView = v.findViewById(R.id.notificationsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val userId = ElsaPreferences.getUserId(activity)

        val callback = object : ApolloCall.Callback<NotificationsForUserQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Couldn't fetch notifications for $userId")
            }

            override fun onResponse(response: Response<NotificationsForUserQuery.Data>) {
                val resList = response.data()?.NotificationByUser()
                val data = ArrayList<Notification>()
                resList?.forEach {
                    val noti = Notification(
                        sourceUserName = it.follower_name(),
                        notificationType = if ( it.type() == "share" ) Notification.SHARE else Notification.FOLLOW
                    )
                    data.add(noti)
                }
                activity.runOnUiThread {
                    recyclerView.adapter = NotificationsAdapter(data)
                }
            }
        }

        Client.queryNotificationsFor(userId, callback)

        return v
    }
}
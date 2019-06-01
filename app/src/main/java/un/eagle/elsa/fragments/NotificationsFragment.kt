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
import un.eagle.elsa.adapters.NotificationsAdapter
class NotificationsFragment : Fragment() {

    companion object {
        const val TAG = "Eagle.NotificationsFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = getString(R.string.title_notifications)

        val v = inflater.inflate(R.layout.fragment_notifications,null)


        val recyclerView : RecyclerView = v.findViewById(R.id.notificationsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val data = MockData.notifications()

        recyclerView.adapter = NotificationsAdapter(data)

        return v
    }
}
package un.eagle.elsa.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.data.model.Notification

class NotificationsAdapter(private val notifications: List<Notification>) : RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {


    override fun getItemCount(): Int {  return notifications.size }

    override fun getItemViewType(index: Int): Int {
        return notifications[index].notificationType
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): NotificationViewHolder {
        val id = if ( viewType == Notification.SHARE ) R.layout.item_notification_share else R.layout.item_notification_follow
        val v = LayoutInflater.from(viewGroup.context).inflate(id, viewGroup, false)
        return NotificationViewHolder(v)

    }

    override fun onBindViewHolder(viewHolder: NotificationViewHolder, index: Int) {

        val res = viewHolder.notificationTextView.resources
        val item = notifications[index]
        viewHolder.notificationTextView.text =
            if ( item.isShare() )
                String.format(res.getString(R.string.share_notification_text), item.sourceUserName)
            else
                String.format(res.getString(R.string.follow_notification_text), item.sourceUserName)

    }

    class NotificationViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val notificationTextView = v.findViewById(R.id.notificationItem_notificationText) as TextView
    }
}
package un.eagle.elsa.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import un.eagle.elsa.R
import un.eagle.elsa.activities.FollowersActivity
import un.eagle.elsa.activities.FollowingActivity
import un.eagle.elsa.activities.WritePostActivity

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

        logOutButton.setOnClickListener { logOut() }
        updateDataButton.setOnClickListener { goToUpdateDataActivity() }
        followersTV.setOnClickListener { goToFollowersActivity() }
        followingTV.setOnClickListener { goToFollowingActivity() }
        return v
    }
}
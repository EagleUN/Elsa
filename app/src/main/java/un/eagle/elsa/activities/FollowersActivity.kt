package un.eagle.elsa.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import un.eagle.elsa.R
import un.eagle.elsa.fragments.SimpleUserListFragment




class FollowersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followers)

        val fragment = SimpleUserListFragment()
        val args = Bundle()
        args.putInt(SimpleUserListFragment.TYPE, SimpleUserListFragment.FOLLOWERS)
        fragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }
}

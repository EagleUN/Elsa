package un.eagle.elsa.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import un.eagle.elsa.R
import un.eagle.elsa.fragments.SimpleUserListFragment

class FollowingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)

        val fragment = SimpleUserListFragment()
        val args = Bundle()
        args.putInt(SimpleUserListFragment.TYPE, SimpleUserListFragment.FOLLOWING)
        fragment.arguments = args

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
    }
}

package un.eagle.elsa.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.R
import un.eagle.elsa.fragments.PostsFragment
import un.eagle.elsa.fragments.NotificationsFragment
import un.eagle.elsa.fragments.ProfileFragment
import un.eagle.elsa.fragments.OtherUserListFragment

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.MainActivity"
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                goToHomeTab()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                loadFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                loadFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_userList -> {
                loadFragment(OtherUserListFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun goToHomeTab() {
        val homeFeed = PostsFragment()
        val args = Bundle()
        args.putInt(PostsFragment.TYPE, PostsFragment.HOME)
        homeFeed.arguments = args
        loadFragment(homeFeed)
    }

    private fun loadFragment(fragment: Fragment) : Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment).commit()
        return true
    }

    private fun goToSignInActivity() {
        val intent = Intent (this, SignInActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = ElsaPreferences.getUserId(this)
        Log.d(TAG, "userId: $userId")

        //check if user is signed in
        if ( userId != "" )
        {
            setContentView(R.layout.activity_main)
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            goToHomeTab()
        };
        else
        {
            goToSignInActivity()
        }
    }
}

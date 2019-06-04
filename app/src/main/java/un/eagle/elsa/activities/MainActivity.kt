package un.eagle.elsa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import un.eagle.elsa.Constants
import un.eagle.elsa.R
import un.eagle.elsa.fragments.HomeFragment
import un.eagle.elsa.fragments.NotificationsFragment
import un.eagle.elsa.fragments.ProfileFragment
import un.eagle.elsa.graphql.Client

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.MainActivity"
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(HomeFragment())
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
        }
        false
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

        val preferences = getSharedPreferences(Constants.APP_PACKAGE, Context.MODE_PRIVATE)
        val userId = preferences.getString(Constants.Preferences.USER_ID, "")

        Log.d(TAG, "userId: $userId")

        //check if user is signed in
        if ( userId != "" )
        {
            setContentView(R.layout.activity_main)
            val navView: BottomNavigationView = findViewById(R.id.nav_view)
            navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            loadFragment(HomeFragment())
        };
        else
        {
            goToSignInActivity()
        }

        Client.fetchAllUsers()
    }
}

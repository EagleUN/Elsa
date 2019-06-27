package un.eagle.elsa.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.iid.FirebaseInstanceId
import un.eagle.elsa.Constants
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.R
import un.eagle.elsa.fragments.PostsFragment
import un.eagle.elsa.fragments.NotificationsFragment
import un.eagle.elsa.fragments.ProfileFragment
import un.eagle.elsa.fragments.OtherUserListFragment
import un.eagle.elsa.graphql.Client
import un.eagle.elsa.services.NotificationsService

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

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun initFirebase(userId: String) {
        FirebaseInstanceId.getInstance().instanceId
        .addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(TAG, "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result?.token
            // Get new Instance ID token
            token?.let {
                Log.d(TAG, "token is $token")
                NotificationsService.sendRegistrationToServer(userId, token)
            }
        })
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        val userId = ElsaPreferences.getUserId(this)
        val sessionToken = ElsaPreferences.getSessionJwt(this)
        Log.d(TAG, "userId: $userId")

        //check if user is signed in
        if ( sessionToken != "" )
        {
            initFirebase(userId)
            Client.reset(sessionToken)
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

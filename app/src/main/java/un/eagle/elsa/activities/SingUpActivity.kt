package un.eagle.elsa.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import un.eagle.elsa.R

class SingUpActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.SignUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        var goToSignIn = findViewById<Button>(R.id.goToSignIn_button)
        goToSignIn.setOnClickListener { goToSignInActivity() }
    }

    private fun goToSignInActivity() {
        val intent = Intent (this, SignInActivity::class.java)
        startActivity(intent)
    }

    /**
     * Disables going back to other activity from SignUpActivity with the back key.
     */
    override fun onBackPressed() {
        moveTaskToBack(true);
    }

}

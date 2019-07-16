package un.eagle.elsa.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.R
import un.eagle.elsa.graphql.Client
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import un.eagle.elsa.CreateUserMutation
import un.eagle.elsa.ElsaPreferences


class SingUpActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.SignUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        val userNameET                 = findViewById<EditText>(R.id.signUp_user_name)
        val userLastNameET             = findViewById<EditText>(R.id.signUp_user_lastName)
        val userUsernameET             = findViewById<EditText>(R.id.signUp_user_username)
        val userEmailET                = findViewById<EditText>(R.id.signUp_user_email)
        val userPasswordET             = findViewById<EditText>(R.id.signUp_user_password)
        val userPasswordConfirmationET = findViewById<EditText>(R.id.signUp_user_passwordConfirmation)

        val signUpButton = findViewById<Button>(R.id.signUp_button)
        val goToSignIn = findViewById<Button>(R.id.goToSignIn_button)
        val context = this

        val callback = object : ApolloCall.Callback<CreateUserMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.e(TAG, "failed", e)
            }

            override fun onResponse(response: Response<CreateUserMutation.Data>) {
                Log.d(TAG, "Created user successfully" )
                val userId = response.data()?.createUser()?.id()
                Log.d(TAG, "data = ${response.data()}")
                Log.d(TAG, "createUser=  ${response.data()?.createUser()}")
                Log.d(TAG, "ID of created user is: $userId")
                if ( userId == null ) return

                ElsaPreferences.setUserId(context, userId)

                runOnUiThread {
                    Toast.makeText(
                        applicationContext,
                        resources.getString(R.string.user_created_successfully),
                        Toast.LENGTH_SHORT).show()
                }

                goToMainActivity()
            }
        }

        goToSignIn.setOnClickListener { goToSignInActivity() }
        signUpButton.setOnClickListener {
            val name = userNameET.text.toString()
            val lastName = userLastNameET.text.toString()
            val username = userUsernameET.text.toString()
            val email = userEmailET.text.toString()
            val password = userPasswordET.text.toString()
            val passwordConfirmation = userPasswordConfirmationET.text.toString()
            Client.createNewUser(
                name = name,
                lastName = lastName,
                username = username,
                email = email,
                password = password,
                passwordConfirmation = passwordConfirmation,
                callback = callback );
        }
    }

    private fun signUp() {

    }

    private fun goToSignInActivity() {
        val intent = Intent (this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    /**
     * Disables going back to other activity from SignUpActivity with the back key.
     */
    override fun onBackPressed() {
        moveTaskToBack(true);
    }

}

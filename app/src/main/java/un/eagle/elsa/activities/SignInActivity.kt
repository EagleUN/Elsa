package un.eagle.elsa.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.CreateNewUserSessionMutation
import un.eagle.elsa.ElsaPreferences

import un.eagle.elsa.R
import un.eagle.elsa.graphql.Client

class SignInActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.SignInActivity"
    }

    data class LoginViewModel(val email: String, val password: String, val login : Button) {
        fun loginDataChanged(username: String, password: String) {
            if (!isEmailValid(username)) {
                // R.string.invalid_username
                login.isEnabled = false
            } else if (!isPasswordValid(password)) {
                //R.string.invalid_password
                login.isEnabled = false
            } else {
                //isDataValid = true
                login.isEnabled = true
            }
        }

        // A placeholder username validation check
        private fun isEmailValid(username: String): Boolean {
            return if (username.contains('@')) {
                Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                username.isNotBlank()
            }
        }

        // A placeholder password validation check
        private fun isPasswordValid(password: String): Boolean {
            return password.length > 8
        }
    }

    private lateinit var loginViewModel: LoginViewModel
    private var loading : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")

        setContentView(R.layout.activity_login)

        loading = findViewById(R.id.loading)
        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login_button)
        var goToSignUp = findViewById<Button>(R.id.goToSignUp_button)
        loginViewModel = LoginViewModel("","",login)
        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading?.visibility = View.VISIBLE
                login(username.text.toString(), password.text.toString())
            }
        }

        goToSignUp.setOnClickListener { goToSignUpActivity() }
    }

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job

        val context = this
        val loginCallback = object : ApolloCall.Callback<CreateNewUserSessionMutation.Data> (){
            override fun onFailure(e: ApolloException) {
                showLoginFailed(R.string.login_failed)
            }

            override fun onResponse(response: Response<CreateNewUserSessionMutation.Data>) {
                val r = response.data()?.createNewUserSession()
                val token = r?.jwt()
                if ( r == null || token == null ) {
                    showLoginFailed(R.string.login_failed)
                }
                else {

                    ElsaPreferences.setUserId(context, token)
                    showLogginSuccessful()
                    goToMainActivity()
                }
            }
        }

        Client.createUserSession(email=username, password = password, callback = loginCallback  )
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun goToSignUpActivity() {
        val intent = Intent (this, SingUpActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showLogginSuccessful() {
        val activity = this
        this.runOnUiThread {
            val welcome = getString(R.string.welcome)
            Toast.makeText(activity, "$welcome", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Log.d(TAG, "Login failed")
        val activity = this
        this.runOnUiThread {
            Toast.makeText(activity, activity.resources.getString(errorString), Toast.LENGTH_LONG).show()
        }
        loading?.visibility = View.INVISIBLE
    }

    /**
     * Disables going back to other activity from SignInActivity with the back key.
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

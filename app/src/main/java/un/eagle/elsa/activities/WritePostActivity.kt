package un.eagle.elsa.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import un.eagle.elsa.CreatePostMutation
import un.eagle.elsa.ElsaPreferences
import un.eagle.elsa.R
import un.eagle.elsa.graphql.Client

class WritePostActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.WritePostActivity"
    }

    lateinit var postEditText : EditText

    private fun postIt() {
        val content : String = postEditText.text.toString()
        val userId = ElsaPreferences.getUserId(this)
        val activity = this

        val callback = object : ApolloCall.Callback<CreatePostMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                Log.d(TAG, "Failed to post it")
            }

            override fun onResponse(response: Response<CreatePostMutation.Data>) {
                activity.runOnUiThread {
                    val msg = activity.resources.getString(R.string.posted_successfully)
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
                    activity.finish()
                }
            }
        }

        Client.createPost(userId, content, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_write_post)

        val postButton : Button = findViewById(R.id.postButton)
        postEditText = findViewById(R.id.postEditText)

        postButton.setOnClickListener{ postIt() }
    }
}

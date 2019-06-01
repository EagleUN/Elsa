package un.eagle.elsa.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import un.eagle.elsa.R

class WritePostActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Eagle.WritePostActivity"
    }

    lateinit var postEditText : EditText

    private fun postIt() {
        val post : String = postEditText.text.toString()
        Log.d(TAG, "Post: \"$post\"");
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

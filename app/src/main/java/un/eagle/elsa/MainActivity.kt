package un.eagle.elsa

import android.content.Intent;
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import un.eagle.elsa.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var intent = Intent(this, LoginActivity::class.java);
        startActivity(intent);
    }
}

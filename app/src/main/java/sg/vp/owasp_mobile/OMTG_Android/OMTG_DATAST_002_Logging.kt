package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class OMTG_DATAST_002_Logging : AppCompatActivity() {
    // Tag can be used for logging
    var TAG = "OMTG_DATAST_002_Logging"
    var usernameText: EditText? = null
    var passwordText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_002__logging)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // find elements
        usernameText = findViewById<View>(R.id.loggingUsername) as EditText
        passwordText = findViewById<View>(R.id.loggingPassword) as EditText
        val btnLogin = findViewById<View>(R.id.loginButton) as Button
        // create click listener for login
        val oclbtnLogin = View.OnClickListener { CreateLogs(usernameText!!.text.toString(), passwordText!!.text.toString()) }
        // assign click listener to the Decrypt button (btnDecrypt)
        btnLogin.setOnClickListener(oclbtnLogin)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun CreateLogs(username: String, password: String) { // error log
        Log.e(TAG, "User successfully logged in. User: $username Password: $password")
        println("WTF, Logging Class should be used instead.")
        Toast.makeText(this, "Log output has been created", Toast.LENGTH_LONG).show()
    }
}
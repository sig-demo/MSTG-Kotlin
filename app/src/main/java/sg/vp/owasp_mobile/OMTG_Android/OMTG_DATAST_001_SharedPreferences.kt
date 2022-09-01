package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View

class OMTG_DATAST_001_SharedPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__shared_preference)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val sharedPref = getSharedPreferences("key", Context.MODE_WORLD_READABLE)
        val editor = sharedPref.edit()
        editor.putString("username", "administrator")
        editor.putString("password", "supersecret")
        editor.commit()
    }
}
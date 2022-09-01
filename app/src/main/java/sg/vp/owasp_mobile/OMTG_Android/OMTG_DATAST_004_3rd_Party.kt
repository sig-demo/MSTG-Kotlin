package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button

class OMTG_DATAST_004_3rd_Party : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_004__3rd_party)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // find elements
        val btnCrash = findViewById<View>(R.id.crashButton) as Button
        // create click listener for crash
        val oclbtnCrash = View.OnClickListener { CrashApp() }
        // assign click listener
        btnCrash.setOnClickListener(oclbtnCrash)
    }

    private fun CrashApp() {
        throw RuntimeException("This is a crash")
    }
}
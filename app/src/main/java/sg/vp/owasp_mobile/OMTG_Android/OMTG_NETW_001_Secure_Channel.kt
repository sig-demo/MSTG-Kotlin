package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.webkit.WebView

class OMTG_NETW_001_Secure_Channel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__netw_001__secure__channel)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val insecure = findViewById<View>(R.id.webView1) as WebView
        val secure = findViewById<View>(R.id.webView2) as WebView
        // plane http call
        insecure.loadUrl(resources.getString(R.string.url_example))
        // secure https call
        secure.loadUrl(resources.getString(R.string.url_example_ssl))
    }
}
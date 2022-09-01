package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button

// Project from jduck, https://github.com/jduck/VulnWebView/
class OMTG_ENV_005_WebView_Remote : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__env_005__web_view)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val button = findViewById<View>(R.id.button1) as Button
        button.setOnClickListener {
            // Perform action on click
            val myWebView = findViewById<View>(R.id.webView1) as WebView
            myWebView.reload()
        }
        val myWebView = findViewById<View>(R.id.webView1) as WebView
        myWebView.webChromeClient = WebChromeClient()
        // not a good idea!
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true
        val jsInterface = OMTG_ENV_005_JS_Interface(this)
        // terrible idea!
        myWebView.addJavascriptInterface(jsInterface, "Android")
        // woot.
        myWebView.loadUrl("https://rawgit.com/sushi2k/AndroidWebView/master/webview.htm")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my, menu)
        return true
    }
}
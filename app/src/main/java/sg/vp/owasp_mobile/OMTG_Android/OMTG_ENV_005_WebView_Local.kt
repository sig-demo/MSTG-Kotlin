package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button

class OMTG_ENV_005_WebView_Local : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__env_005__web_view__local)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val button = findViewById<View>(R.id.button2) as Button
        button.setOnClickListener {
            // Perform action on click
            val myWebView = findViewById<View>(R.id.webView2) as WebView
            myWebView.reload()
        }
        val myWebView = findViewById<View>(R.id.webView2) as WebView
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowFileAccessFromFileURLs = true
        myWebView.webChromeClient = WebChromeClient()
        myWebView.addJavascriptInterface(JavaScriptInterface(), "jsinterface")
        //        OMTG_ENV_005_JS_Interface jsInterface = new OMTG_ENV_005_JS_Interface(this);
//        myWebView.addJavascriptInterface(jsInterface, "Android");
        myWebView.loadUrl("file:///android_asset/local.htm")
        //        setContentView(myWebView);
    }

    internal inner class JavaScriptInterface {
        @get:JavascriptInterface
        val someString: String
            get() = "string"
    }
}
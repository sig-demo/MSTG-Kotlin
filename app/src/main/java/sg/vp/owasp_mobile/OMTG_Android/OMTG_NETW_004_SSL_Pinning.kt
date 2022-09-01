package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class OMTG_NETW_004_SSL_Pinning : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__netw_004__ssl__pinning)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        // Quick and dirty fix http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
        val thread = Thread(Runnable {
            try {
                val sslPinning = SSLPinning()
                sslPinning.onCreate()
                val myurl = URL("https://www.example.com")
                val con = myurl.openConnection() as HttpsURLConnection
                val ins = con.inputStream
                val isr = InputStreamReader(ins)
                val `in` = BufferedReader(isr)
                var inputLine: String?
                while (`in`.readLine().also { inputLine = it } != null) {
                    println(inputLine)
                }
                `in`.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start()
    }
}
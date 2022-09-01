package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import java.io.*
import java.net.MalformedURLException
import java.net.URL
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class OMTG_NETW_004_SSL_Pinning_Certificate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__netw_004__ssl__pinning__certificate)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        try {
            HTTPSssLPinning()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }
    }

    @Throws(CertificateException::class, IOException::class, KeyStoreException::class, NoSuchAlgorithmException::class, KeyManagementException::class)
    private fun HTTPSssLPinning() {
        val cf = CertificateFactory.getInstance("X.509")
        // Generate the certificate using the certificate file under res/raw/certificate
        val caInput: InputStream = BufferedInputStream(resources.openRawResource(R.raw.certificate))
        val ca = cf.generateCertificate(caInput)
        caInput.close()
        // Create a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        val keyStoreAlias: Enumeration<*> = keyStore.aliases()
        while (keyStoreAlias.hasMoreElements()) println("KeyStore: " + keyStoreAlias.nextElement().toString())
        // Create a TrustManager that trusts the CAs in our KeyStore
        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)
        // Create an SSLContext that uses our TrustManager
        val context = SSLContext.getInstance("TLS")
        context.init(null, tmf.trustManagers, null)
        // Tell the URLConnection to use a SocketFactory from our SSLContext
// Quick and dirty fix http://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception
        val thread = Thread(Runnable {
            try {
                var url: URL? = null
                try {
                    url = URL("https://example.com")
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
                val urlConnection = url!!.openConnection() as HttpsURLConnection
                urlConnection.sslSocketFactory = context.socketFactory
                // Get response and print it to stdout
// Create all-trusting host name verifier
                val hostnameVerifier = HostnameVerifier { hostname, session ->
                    val hv = HttpsURLConnection.getDefaultHostnameVerifier()
                    if ("example.com".contains(hostname)) {
                        true
                    } else {
                        hv.verify("something.com", session)
                    }
                }
                HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier)
                val `in` = urlConnection.inputStream
                val isr = InputStreamReader(`in`)
                val br = BufferedReader(isr)
                var inputLine: String?
                while (br.readLine().also { inputLine = it } != null) {
                    println(inputLine)
                }
                `in`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
        thread.start()
    }
}
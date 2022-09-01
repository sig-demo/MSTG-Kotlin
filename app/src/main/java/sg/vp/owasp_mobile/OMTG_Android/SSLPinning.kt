package sg.vp.owasp_mobile.OMTG_Android

import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

/**
 * Created by sven on 11/4/17.
 */
class SSLPinning {
    //    @Override
    fun onCreate() { // This must be performed once at the start of the application to install required handlers
        try {
            val ctx = SSLContext.getInstance("TLS")
            // Use our hardend version
            ctx.init(null, arrayOf<TrustManager>(HardenedX509TrustManager<Any?>(null)), null)
            // Set the default SSL Factory of the application to the new instance we have created
// All HTTPS / SSL access libraries will now use our new class instead of the system
// default
            HttpsURLConnection.setDefaultSSLSocketFactory(ctx.socketFactory)
        } catch (e: NoSuchAlgorithmException) { // Log or handle the exception accordingly
// We exit here for safety reason as the certificate pinning mechanism is not enabled
            System.exit(-1)
        } catch (e: KeyManagementException) { // Log or handle the exception accordingly
// We exit here for safety reason as the certificate pinning mechanism is not enabled
            System.exit(-1)
        } catch (e: KeyStoreException) { // Log or handle the exception accordingly
// We exit here for safety reason as the certificate pinning mechanism is not enabled
            System.exit(-1)
        }
    }
}
package sg.vp.owasp_mobile.OMTG_Android

import android.util.Log
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * Created by sven on 11/4/17.
 */
class HardenedX509TrustManager<E>(keystore: KeyStore?) : X509TrustManager {
    private var standardTrustManager: X509TrustManager? = null
    /**
     * // * @see javax.net.ssl.X509TrustManager#checkClientTrusted(X509Certificate[],String authType)
     */
    @Throws(CertificateException::class)
    override fun checkClientTrusted(certificates: Array<X509Certificate>, authType: String) {
        standardTrustManager!!.checkClientTrusted(certificates, authType)
    }

    /**
     * //     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(X509Certificate[],String authType)
     */
    @Throws(CertificateException::class)
    override fun checkServerTrusted(certificates: Array<X509Certificate>, authType: String) {
        standardTrustManager!!.checkServerTrusted(certificates, authType)
        for (cert in certificates) {
            val issuer_name = cert.issuerDN.name
            if (issuer_name.indexOf(",O=$TRUSTED_CA_AUTHORITY,") == -1) throw CertificateException()
            Log.w("Error", issuer_name)
        }
    }

    /**
     * @see javax.net.ssl.X509TrustManager.getAcceptedIssuers
     */
    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return standardTrustManager!!.acceptedIssuers
        //return this.standardTrustManager.getAcceptedIssuers();
    }

    companion object {
        // Change this to the authority you want to pin the certificate into
        const val TRUSTED_CA_AUTHORITY = "PortSwigger"
    }

    /**
     * Constructor for EasyX509TrustManager.
     */
    init {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(keystore)
        val trustmanagers = factory.trustManagers
        if (trustmanagers.size == 0) {
            throw NoSuchAlgorithmException("no trust manager found")
        }
        standardTrustManager = trustmanagers[0] as X509TrustManager
    }
}
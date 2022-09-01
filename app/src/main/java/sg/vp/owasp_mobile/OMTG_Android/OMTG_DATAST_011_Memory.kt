package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import com.tozny.crypto.android.AesCbcWithIntegrity.CipherTextIvMac
import com.tozny.crypto.android.AesCbcWithIntegrity.SecretKeys
import com.tozny.crypto.android.AesCbcWithIntegrity.decryptString
import com.tozny.crypto.android.AesCbcWithIntegrity.keys
import java.io.UnsupportedEncodingException
import java.security.GeneralSecurityException
import java.security.InvalidKeyException

class OMTG_DATAST_011_Memory : AppCompatActivity() {
    // Tag can be used for logging
    var TAG = "OMTG_DATAST_011_Memory"
    var plainText: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_011__memory)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        decryptString()
    }

    // Using Java-AES-Crypto, https://github.com/tozny/java-aes-crypto
    fun decryptString() { // BTW: Really bad idea, as this is the raw private key. Should be stored in the keystore
        val rawKeys = "4zInk+d4jlQ3m1B1ELctxg==:4aZtzwpbniebvM7yC4/GIa2ZmJpSzqrAFtVk91Rm+Q4="
        var privateKey: SecretKeys? = null
        try {
            privateKey = keys(rawKeys)
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
        }
        val cipherTextString = "6WpfZkgKMJsPhHNhWoSpVg==:6/TgUCXrAuAa2lUMPWhx8hHOWjWEHFp3VIsz3Ws37ZU=:C0mWyNQjcf6n7eBSFzmkXqxdu55CjUOIc5qFw02aVIfQ1CI8axsHijTJ9ZW6ZfEE"
        val cipherTextIvMac = CipherTextIvMac(cipherTextString)
        try {
            plainText = decryptString(cipherTextIvMac, privateKey!!)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
        }
    }
}
package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import android.security.KeyChain
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.io.IOException

class OMTG_DATAST_001_KeyChain : AppCompatActivity() {
    //private static final String DEFAULT_ALIAS = "My Key Chain";
//Request code used when starting the activity using the KeyChain install intent
//private static final int INSTALL_KEYCHAIN_CODE = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__key_chain)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        installPkcs12()
    }

    private fun installPkcs12() {
        try { // debug - filenames in assets directory
            val f = assets.list("")
            for (f1 in f!!) {
                Log.v("names", f1)
            }
            val bis = BufferedInputStream(assets.open(PKCS12_FILENAME))
            val keychain = ByteArray(bis.available())
            bis.read(keychain)
            val installIntent = KeyChain.createInstallIntent()
            installIntent.putExtra(KeyChain.EXTRA_PKCS12, keychain)
            startActivity(installIntent)
            //            installIntent.putExtra(KeyChain.EXTRA_NAME, DEFAULT_ALIAS);
//            startActivityForResult(installIntent, INSTALL_KEYCHAIN_CODE);
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val PKCS12_FILENAME = "server.p12"
    }
}
package sg.vp.owasp_mobile.OMTG_Android

import android.os.Build
import android.os.Bundle
import android.security.KeyPairGeneratorSpec
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.security.auth.x500.X500Principal

// Code Basis from
// https://github.com/obaro/SimpleKeystoreApp/blob/master/app/src/main/java/com/sample/foo/simplekeystoreapp/MainActivity.java
class OMTG_DATAST_001_KeyStore : AppCompatActivity() {
    var TAG = "OMTG_DATAST_001_KeyStore"
    var startText: EditText? = null
    var decryptedText: EditText? = null
    var encryptedText: EditText? = null
    lateinit var keyStore: KeyStore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        setContentView(R.layout.activity_omtg__datast_001__key_store)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val btnEncrypt: Button
        val btnDecrypt: Button
        // find elements
        encryptedText = findViewById<View>(R.id.encryptedText) as EditText
        decryptedText = findViewById<View>(R.id.decryptedText) as EditText
        startText = findViewById<View>(R.id.startText) as EditText
        btnDecrypt = findViewById<View>(R.id.KeyStoreDecrypt) as Button
        btnEncrypt = findViewById<View>(R.id.KeyStoreEncrypt) as Button
        // create click listener for encryption
        val oclbtnEncrypt = View.OnClickListener { encryptString("Dummy") }
        // assign click listener to the Decrypt button (btnDecrypt)
        btnEncrypt.setOnClickListener(oclbtnEncrypt)
        // create click listener for decryption
        val oclbtnDecrypt = View.OnClickListener { decryptString("Dummy") }
        // assign click listener to the Decrypt button (btnDecrypt)
        btnDecrypt.setOnClickListener(oclbtnDecrypt)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        createNewKeys()
    }

    fun createNewKeys() {
        val alias = "Dummy"
        try { // Create new key if needed
            if (!keyStore!!.containsAlias(alias)) {
                val start = Calendar.getInstance()
                val end = Calendar.getInstance()
                end.add(Calendar.YEAR, 1)
                var spec: KeyPairGeneratorSpec? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    spec = KeyPairGeneratorSpec.Builder(this)
                            .setAlias(alias)
                            .setSubject(X500Principal("CN=Sample Name, O=Android Authority"))
                            .setSerialNumber(BigInteger.ONE)
                            .setStartDate(start.time)
                            .setEndDate(end.time)
                            .build()
                }
                val generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")
                generator.initialize(spec)
                val keyPair = generator.generateKeyPair()
                Toast.makeText(applicationContext, "Key Pair \"Dummy\" created.", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, "Key Pair \"Dummy\" already created.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Exception " + e.message + " occured", Toast.LENGTH_LONG).show()
            Log.e(TAG, Log.getStackTraceString(e))
        }
    }

    fun encryptString(alias: String?) {
        try {
            val privateKeyEntry = keyStore!!.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val publicKey = privateKeyEntry.certificate.publicKey as RSAPublicKey
            val test = "12345678"
            //            Log.v(TAG, "Public key [byte format]: " + publicKey.getEncoded());
            Log.v(TAG, "test log: $test")
            Log.e(TAG, publicKey.toString())
            val initialText = startText!!.text.toString()
            if (initialText.isEmpty()) {
                Toast.makeText(this, "Enter text in the 'Initial Text' widget", Toast.LENGTH_LONG).show()
                return
            }
            val inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL")
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val outputStream = ByteArrayOutputStream()
            val cipherOutputStream = CipherOutputStream(
                    outputStream, inCipher)
            cipherOutputStream.write(initialText.toByteArray(charset("UTF-8")))
            cipherOutputStream.close()
            val vals = outputStream.toByteArray()
            encryptedText!!.setText(Base64.encodeToString(vals, Base64.DEFAULT))
            Log.e(TAG, Base64.encodeToString(vals, Base64.DEFAULT))
        } catch (e: Exception) {
            Toast.makeText(this, "Exception " + e.message + " occured", Toast.LENGTH_LONG).show()
            Log.e(TAG, Log.getStackTraceString(e))
        }
    }

    fun decryptString(alias: String?) {
        try {
            val privateKeyEntry = keyStore!!.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            //            RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
//            RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
            Log.e(TAG, Arrays.toString(privateKeyEntry.privateKey.encoded))
            val output = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
            val cipherText = encryptedText!!.text.toString()
            val cipherInputStream = CipherInputStream(
                    ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output)
            val values = ArrayList<Byte>()
            var nextByte: Int
            while (cipherInputStream.read().also { nextByte = it } != -1) {
                values.add(nextByte.toByte())
            }
            val bytes = ByteArray(values.size)
            for (i in bytes.indices) {
                bytes[i] = values[i]
            }
            val finalText = String(bytes, 0, bytes.size, Charsets.UTF_8)
            decryptedText!!.setText(finalText)
        } catch (e: Exception) {
            Toast.makeText(this, "Exception " + e.message + " occured", Toast.LENGTH_LONG).show()
            Log.e(TAG, Log.getStackTraceString(e))
        }
    }
}
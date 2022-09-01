package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import sg.vp.owasp_mobile.OMTG_Android.insecurestorageofencryptionkeys.DeCryptor
import sg.vp.owasp_mobile.OMTG_Android.insecurestorageofencryptionkeys.EnCryptor
import sg.vp.owasp_mobile.OMTG_Android.insecurestorageofencryptionkeys.SecurityConstants
import java.io.IOException
import java.math.BigInteger
import java.security.*
import java.security.cert.CertificateException
import java.util.*
import javax.crypto.*
import javax.crypto.spec.SecretKeySpec
import javax.security.auth.x500.X500Principal

class OMTG_DATAST_001_SNPS_BadEncrypt : AppCompatActivity() {
    private var pref: SharedPreferences? = null
    private var plainEt: EditText? = null
    private var encryptedTv: TextView? = null
    private var decryptedTv: TextView? = null
    private var encryptor: EnCryptor? = null
    private var decryptor: DeCryptor? = null
    private var keyStore: KeyStore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_encrypt)
        initView()
        pref = getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        encryptor = EnCryptor()
        decryptor = DeCryptor()
        try {
            keyStore = KeyStore.getInstance(SecurityConstants.Companion.ANDROID_KEY_STORE)
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        }
        try {
            keyStore!!.load(null)
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    private fun initView() {
        plainEt = findViewById(R.id.plain_et)
        encryptedTv = findViewById(R.id.encrypted_tv)
        decryptedTv = findViewById(R.id.decrypted_tv)
        val onClickListenerEnc = View.OnClickListener {
            if (!plainEt!!.getText().toString().isEmpty()) {
                val encryptedData = encryptText(plainEt!!.getText().toString())
                encryptedTv!!.setText(encryptedData)
                saveEncryptedDataInSharedPref(encryptedData)
                plainEt!!.setError(null)
            } else {
                plainEt!!.setError("Write something here")
            }
        }
        val onClickListenerDec = View.OnClickListener {
            val encryptedData = decryptText(retrieveEncryptedData())
            decryptedTv!!.setText(encryptedData)
        }
        val onClickListenerClr = View.OnClickListener { clearAll() }
        findViewById<View>(R.id.encryption_btn).setOnClickListener(onClickListenerEnc)
        findViewById<View>(R.id.decryption_btn).setOnClickListener(onClickListenerDec)
        findViewById<View>(R.id.clear_btn).setOnClickListener(onClickListenerClr)
    }

    private fun saveEncryptedDataInSharedPref(encryptedData: String) {
        val editor = pref!!.edit()
        editor.putString(getString(R.string.encrypted_data), encryptedData)
        editor.apply()
    }

    private fun retrieveEncryptedData(): String? {
        return pref!!.getString(getString(R.string.encrypted_data), "")
    }

    private fun clearAll() {
        val dataEditor = pref!!.edit()
        dataEditor.putString(getString(R.string.encrypted_data), null)
        dataEditor.apply()
        plainEt!!.setText("")
        encryptedTv!!.text = ""
        decryptedTv!!.text = ""
    }

    private fun decryptText(encryptedText: String?): String? {
        try {
            val valueBytes = Base64.decode(encryptedText, Base64.DEFAULT)
            return decryptor!!.decryptData(getSecretKey(SecurityConstants.Companion.SAMPLE_ALIAS), valueBytes, FIXED_IV.toByteArray())
        } catch (e: Exception) {
            Log.e(TAG, "decryptText() called with: " + e.message, e)
        }
        return ""
    }

    private fun encryptText(plainText: String): String {
        try {
            val encryptedText = encryptor!!.encryptText(generateSecretKey(SecurityConstants.Companion.SAMPLE_ALIAS), plainText, FIXED_IV.toByteArray())
            return Base64.encodeToString(encryptedText, Base64.DEFAULT)
        } catch (e: Exception) {
            Log.e(TAG, "encryptText() called with: " + e.message, e)
        }
        return ""
    }

    @Throws(NoSuchProviderException::class, NoSuchAlgorithmException::class, InvalidAlgorithmParameterException::class, KeyStoreException::class, IllegalBlockSizeException::class, InvalidKeyException::class, BadPaddingException::class, UnrecoverableEntryException::class, NoSuchPaddingException::class)
    private fun generateSecretKey(alias: String): Key {
        return if (USE_HARDCODED) {
            hardcoded
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) { /*val keyStore: KeyStore = KeyStore.getInstance(SecurityConstants.ANDROID_KEY_STORE)
                keyStore.load(null)*/
// Generate the RSA key pairs
                if (!keyStore!!.containsAlias(alias)) { // Generate a key pair for encryption
                    val start = Calendar.getInstance()
                    val end = Calendar.getInstance()
                    end.add(Calendar.YEAR, 30)
                    val spec = KeyPairGeneratorSpec.Builder(this)
                            .setAlias(alias)
                            .setSubject(X500Principal("CN=$alias"))
                            .setSerialNumber(BigInteger.TEN)
                            .setStartDate(start.time)
                            .setEndDate(end.time)
                            .build()
                    val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(SecurityConstants.Companion.TYPE_RSA, SecurityConstants.Companion.ANDROID_KEY_STORE)
                    kpg.initialize(spec)
                    kpg.generateKeyPair()
                    //Generate and Store the AES Key
                    generateAndStoreRsaKey()
                }
                getSecretKey(SecurityConstants.Companion.SAMPLE_ALIAS)
            } else {
                val keyGenerator: KeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, SecurityConstants.Companion.ANDROID_KEY_STORE)
                keyGenerator.init(KeyGenParameterSpec.Builder(alias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                        .setRandomizedEncryptionRequired(false)
                        .build())
                keyGenerator.generateKey()
            }
        }
    }

    @Throws(UnrecoverableEntryException::class, NoSuchAlgorithmException::class, KeyStoreException::class, IllegalBlockSizeException::class, InvalidKeyException::class, BadPaddingException::class, NoSuchProviderException::class, NoSuchPaddingException::class)
    private fun getSecretKey(alias: String): SecretKey {
        return if (USE_HARDCODED) {
            SecretKeySpec(hardcoded.encoded, "AES")
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val encryptedKeyB64: String?
                encryptedKeyB64 = pref!!.getString(getString(R.string.encrypted_key), null)
                val encryptedKey = Base64.decode(encryptedKeyB64, Base64.DEFAULT)
                val key = rsaDecrypt(encryptedKey)
                SecretKeySpec(key, "AES")
            } else {
                val secretKeyEntry = keyStore!!.getEntry(alias, null) as KeyStore.SecretKeyEntry
                secretKeyEntry.secretKey
            }
        }
    }

    /**Pre Android M */
    @Throws(NoSuchPaddingException::class, InvalidKeyException::class, NoSuchAlgorithmException::class, KeyStoreException::class, BadPaddingException::class, IllegalBlockSizeException::class, NoSuchProviderException::class, UnrecoverableEntryException::class)
    private fun generateAndStoreRsaKey() {
        var encryptedKeyB64 = pref!!.getString(getString(R.string.encrypted_key), null)
        if (encryptedKeyB64 == null) {
            val key = ByteArray(32)
            val secureRandom = SecureRandom()
            secureRandom.nextBytes(key)
            val encryptedKey = rsaEncrypt(key)
            encryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT)
            val edit = pref!!.edit()
            edit.putString(getString(R.string.encrypted_key), encryptedKeyB64)
            edit.apply()
        }
    }

    @Throws(UnrecoverableEntryException::class, NoSuchAlgorithmException::class, KeyStoreException::class, NoSuchProviderException::class, NoSuchPaddingException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    private fun rsaEncrypt(secret: ByteArray): ByteArray {
        val privateKeyEntry = keyStore!!.getEntry(SecurityConstants.Companion.SAMPLE_ALIAS, null) as KeyStore.PrivateKeyEntry
        // Encrypt the text
        val inputCipher = Cipher.getInstance(SecurityConstants.Companion.RSA_MODE, "AndroidOpenSSL")
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.certificate.publicKey)
        return inputCipher.doFinal(secret)
    }

    @Throws(UnrecoverableEntryException::class, NoSuchAlgorithmException::class, KeyStoreException::class, NoSuchProviderException::class, NoSuchPaddingException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    private fun rsaDecrypt(encrypted: ByteArray): ByteArray {
        val privateKeyEntry = keyStore!!.getEntry(SecurityConstants.Companion.SAMPLE_ALIAS, null) as KeyStore.PrivateKeyEntry
        val cipher = Cipher.getInstance(SecurityConstants.Companion.RSA_MODE, "AndroidOpenSSL")
        cipher.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
        return cipher.doFinal(encrypted)
    }

    /* val key = rsaDecrypt(encryptedKey)*/
    private val hardcoded: Key
        private get() {
            val key = SECRETE_KEY.toByteArray()
            /* val key = rsaDecrypt(encryptedKey)*/return SecretKeySpec(key, "AES")
        }

    companion object {
        private val TAG = OMTG_DATAST_001_SNPS_BadEncrypt::class.java.simpleName
        private const val FIXED_IV = "0123456789ab" //The IV you use in the encryption must be the same one you use in the decryption
        private const val SECRETE_KEY = "my_secrete_keyhu"
        private const val USE_HARDCODED = true
    }
}
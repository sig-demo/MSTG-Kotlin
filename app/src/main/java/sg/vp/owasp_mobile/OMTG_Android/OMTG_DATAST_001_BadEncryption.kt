package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.xor

class OMTG_DATAST_001_BadEncryption : AppCompatActivity() {
    var passwordEditText: EditText? = null
    var btnVerify: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__bad_encryption)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        passwordEditText = findViewById<View>(R.id.BadEnryptionPassword) as EditText
        btnVerify = findViewById<View>(R.id.BadEnryptionButton) as Button
        // create click listener for encryption
        val oclbtnVerify = View.OnClickListener {
            verify(passwordEditText!!.text.toString())
            val result = verify(passwordEditText!!.text.toString())
            result(result)
        }
        // assign click listener to the Decrypt button (btnDecrypt)
        btnVerify!!.setOnClickListener(oclbtnVerify)
    }

    private fun result(result: Boolean) {
        if (result) {
            Toast.makeText(this, "Congratulations, this is the correct password", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private fun verify(str: String): Boolean { // This is the encrypted message
// decrypted = SuperSecret
            val encrypted = "vJqfip28ioydips="
            val encryptedDecoded = Base64.decode(encrypted, Base64.DEFAULT)
            val userPass = encrypt(str)
            if (userPass.size != encryptedDecoded.size) {
                return false
            }
            for (i in userPass.indices) {
                if (userPass[i] != encryptedDecoded[i]) {
                    return false
                }
            }
            return true
        }

        // function used to encrypt a string
        private fun encrypt(str: String): ByteArray {
            val bytes = str.toByteArray()
            for (i in bytes.indices) {
                bytes[i] = (bytes[i] xor 16) as Byte
                val curr: Byte = bytes[i].inv() and 0xff.toByte()
                bytes[i] = curr
            }
            return bytes
        }
    }
}
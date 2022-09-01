package sg.vp.owasp_mobile.OMTG_Android.insecurestorageofencryptionkeys

import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec

class EnCryptor {
    @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class, InvalidAlgorithmParameterException::class, InvalidKeyException::class, UnsupportedEncodingException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun encryptText(secreteKey: Key?, textToEncrypt: String, decryptionIv: ByteArray?): ByteArray {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secreteKey, GCMParameterSpec(128, decryptionIv))
        return cipher.doFinal(textToEncrypt.toByteArray(StandardCharsets.UTF_8))
    }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
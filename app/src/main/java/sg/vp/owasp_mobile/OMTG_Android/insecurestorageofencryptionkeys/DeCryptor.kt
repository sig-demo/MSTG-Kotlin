package sg.vp.owasp_mobile.OMTG_Android.insecurestorageofencryptionkeys

import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec

class DeCryptor {
    @Throws(NoSuchPaddingException::class, NoSuchAlgorithmException::class, InvalidAlgorithmParameterException::class, InvalidKeyException::class, BadPaddingException::class, IllegalBlockSizeException::class)
    fun decryptData(secreteKey: Key?, encryptedData: ByteArray?, encryptionIv: ByteArray?): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, encryptionIv)
        cipher.init(Cipher.DECRYPT_MODE, secreteKey, spec)
        return String(cipher.doFinal(encryptedData))
    }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
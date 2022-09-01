package sg.vp.owasp_mobile.OMTG_Android

import android.accounts.Account
import android.accounts.AccountManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.UserManager
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.net.Socket


class SATActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sat)

        val intent = intent
        // externalData could be tainted, since SATActivity is exported
        val externalData = intent.getStringExtra("externalData")
        externalData?.let {
            Log.i("SAT_Activity", externalData)
            runCommand(it)
        }
        hashB(getPassword().toByteArray(StandardCharsets.UTF_8))

        val hashedPwd = hash(getPassword())
        Log.i("SAT_Activity_PWDHASH", hashedPwd.joinToString())

        val hashedDevId = hash(deviceId())
        Log.i("SAT_Activity_DEVID", hashedDevId.joinToString())

        addAccount("something", deviceId())

        val loc = getSystemService(LOCATION_SERVICE) as LocationManager
        val location = loc.getLastKnownLocation("provider")
        Log.i("SAT_Activity_LOC", location.toString())
        val uName = userName()
        Log.i("SAT_Activity_UNAME", uName)
        val trackerId = getTrackerId(uName, hashedDevId.joinToString())
        Log.i("SAT_Activity_TrackerId", trackerId)
        updateAuthToken(trackerId)
    }

    // For ANDROID_CAPABILITY_LEAK
    fun getLocation(): Location {
        val loc = getSystemService(LOCATION_SERVICE) as LocationManager
        val location = loc.getLastKnownLocation("provider")
        return location!!
    }


    fun userName(): String {
        val user = getSystemService(AppCompatActivity.USER_SERVICE) as UserManager
        return user.userName
    }

    fun getPassword(): String {
        //Requires GET_ACCOUNT
        val account = AccountManager.get(this).accounts[0]
        return AccountManager.get(this).getPassword(account)
    }

    //For MOBILE_ID_MISUSE
    fun addAccount(name: String, password: String) {
        val account = Account(name, "CoolAccount");
        val am = AccountManager.get(this)
        am.addAccountExplicitly(account, password, null);
    }

    fun deviceId(): String {
        val tm = getSystemService(TELEPHONY_SERVICE) as TelephonyManager?
        return tm!!.deviceId
    }

    //For WEAK_PASSWORD_HASH

    fun hash(s: String): ByteArray {
        val digest = MessageDigest.getInstance("MD5");
        digest.update(s.toByteArray(StandardCharsets.UTF_8));
        return digest.digest();
    }

    fun hashB(pwd: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("MD5");
        digest.update(pwd);
        return digest.digest();
    }

    //For OS_CMD_INJECTION
    fun runCommand(cmd: String): Int {
        val process = ProcessBuilder().command(cmd).start()
        return process.waitFor()
    }

    fun getTrackerId(userName: String, mobileId: String): String {
	// For INSECURE_COMMUNICATION
        val socket = Socket("tracker.somesite.xyz", 80)
        socket.getOutputStream().bufferedWriter(Charsets.UTF_8).use { writer ->
            writer.write("$userName:$mobileId\n")
            writer.flush()
        }
        return socket.getInputStream().bufferedReader(Charsets.UTF_8).readLine()
    }

    fun updateAuthToken(token: String) {
        val am = AccountManager.get(this)
        val account = am.accounts[0]
        am.setAuthToken(account, "", token)
    }

}

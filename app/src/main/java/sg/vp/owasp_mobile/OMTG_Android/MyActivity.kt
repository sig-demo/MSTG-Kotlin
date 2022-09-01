package sg.vp.owasp_mobile.OMTG_Android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import sg.vp.owasp_mobile.OMTG_Android.insecurekotlinapp.MainActivity

class MyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_my, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    /** Called when the user clicks the Send button  */ //    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
    fun OMTG_ENV_005_Webview_Remote(view: View?) {
        val intent = Intent(this, OMTG_ENV_005_WebView_Remote::class.java)
        startActivity(intent)
    }

    fun OMTG_ENV_005_Webview_Local(view: View?) {
        val intent = Intent(this, OMTG_ENV_005_WebView_Local::class.java)
        startActivity(intent)
    }

    fun OMTG_CODING_003_Best_Practice(view: View?) {
        val intent = Intent(this, OMTG_CODING_003_Best_Practice::class.java)
        startActivity(intent)
    }

    fun OMTG_CODING_003_SQL_Injection(view: View?) {
        val intent = Intent(this, OMTG_CODING_003_SQL_Injection::class.java)
        startActivity(intent)
    }

    fun OMTG_CODING_003_SQL_Injection_Content_Provider(view: View?) {
        val intent = Intent(this, OMTG_CODING_003_SQL_Injection_Content_Provider::class.java)
        startActivity(intent)
    }

    fun OMTG_CODING_004_Code_Injection(view: View?) {
        val intent = Intent(this, OMTG_CODING_004_Code_Injection::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_BadEncryption(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_BadEncryption::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_KeyChain(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_KeyChain::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_KeyStore(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_KeyStore::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_InternalStorage(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_InternalStorage::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_ExternalStorage(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_ExternalStorage::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_SharedPreferences(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_SharedPreferences::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_SQLite_Not_Encrypted(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_SQLite_Not_Encrypted::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_SQLite_Encrypted(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_SQLite_Encrypted::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_002_Logging(view: View?) {
        val intent = Intent(this, OMTG_DATAST_002_Logging::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_004_3rd_Party(view: View?) {
        val intent = Intent(this, OMTG_DATAST_004_3rd_Party::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_005_Keyboard_Cache(view: View?) {
        val intent = Intent(this, OMTG_DATAST_005_Keyboard_Cache::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_006_Clipboard(view: View?) {
        val intent = Intent(this, OMTG_DATAST_006_Clipboard::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_011_Memory(view: View?) {
        val intent = Intent(this, OMTG_DATAST_011_Memory::class.java)
        startActivity(intent)
    }

    fun OMTG_NETW_001_Secure_Channel(view: View?) {
        val intent = Intent(this, OMTG_NETW_001_Secure_Channel::class.java)
        startActivity(intent)
    }

    fun OMTG_NETW_004_SSL_Pining(view: View?) {
        val intent = Intent(this, OMTG_NETW_004_SSL_Pinning::class.java)
        startActivity(intent)
    }

    fun OMTG_NETW_004_SSL_Pining_Certificate(view: View?) {
        val intent = Intent(this, OMTG_NETW_004_SSL_Pinning_Certificate::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_SNPS_InternalStorage_WorldAccessible(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_SNPS_InternalStorage_WorldAccessible::class.java)
        startActivity(intent)
    }

    fun OMTG_DATAST_001_SNPS_BadEncrypt(view: View?) {
        val intent = Intent(this, OMTG_DATAST_001_SNPS_BadEncrypt::class.java)
        startActivity(intent)
    }

    fun StartInsecureKotlinAppActivity(view: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun start_SAT_Activity(view: View?) {
        val intent = Intent(this, SATActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE"
    }
}
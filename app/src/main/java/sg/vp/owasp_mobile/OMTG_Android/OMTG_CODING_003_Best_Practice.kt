package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File

class OMTG_CODING_003_Best_Practice : AppCompatActivity() {
    var login = false
    var usernameText: EditText? = null
    var passwordText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        initializeDB(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__coding_003__best__practice)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // find elements
        usernameText = findViewById<View>(R.id.sqlInjectionBestPracticeUsername) as EditText
        passwordText = findViewById<View>(R.id.sqlInjectionBestPracticePassword) as EditText
        val btnLogin = findViewById<View>(R.id.sqlInjectionBestPracticeButton) as Button
        // create click listener for login
        val oclbtnLogin = View.OnClickListener {
            login = checkLogin(usernameText!!.text.toString(), passwordText!!.text.toString())
            toastOutput(login)
        }
        // assign click listener to the Decrypt button (btnDecrypt)
        btnLogin.setOnClickListener(oclbtnLogin)
    }

    private fun toastOutput(login: Boolean) {
        if (login) {
            Toast.makeText(this, "User logged in", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Username and/or password wrong", Toast.LENGTH_LONG).show()
        }
    }

    private fun initializeDB(applicationContext: Context) {
        val dbAvailable: File
        dbAvailable = applicationContext.getDatabasePath("authentication-best-practice")
        if (!dbAvailable.exists()) {
            val authentication = openOrCreateDatabase("authentication-best-practice", Context.MODE_PRIVATE, null)
            authentication.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR,Password VARCHAR);")
            authentication.execSQL("INSERT INTO Accounts VALUES('admin','AdminPass');")
            authentication.close()
        }
    }

    private fun checkLogin(username: String, password: String): Boolean {
        var bool = false
        val authentication = openOrCreateDatabase("authentication-best-practice", Context.MODE_PRIVATE, null)
        // Not Possible to get a cursor back when using SQLite Statement
//        String sql = "SELECT * FROM Accounts WHERE Username = ? and Password = ?";
//        authentication.beginTransactionNonExclusive();
//        try {
//            SQLiteStatement stmt = authentication.compileStatement(sql);
//            stmt.bindString(1,username);
//            stmt.bindString(2,password);
//            stmt.execute();
//            stmt.clearBindings();
//            authentication.setTransactionSuccessful();
//        }
//        finally {
//            authentication.endTransaction();
//            authentication.close();
//        }
        try {
            authentication.rawQuery("SELECT * FROM Accounts WHERE Username=? and Password=?", arrayOf(username, password)).use { cursor ->
                if (cursor != null) {
                    if (cursor.moveToFirst()) bool = true
                    cursor.close()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bool
    }
}
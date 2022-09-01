package sg.vp.owasp_mobile.OMTG_Android

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import net.sqlcipher.database.SQLiteDatabase

class OMTG_DATAST_001_SQLite_Encrypted : AppCompatActivity() {
    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__sqlite__secure)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        SQLiteEnc()
    }

    external fun stringFromJNI(): String?

    companion object {
        init {
            System.loadLibrary("native")
        }
    }

    private fun SQLiteEnc() {
        SQLiteDatabase.loadLibs(this)
        val database = getDatabasePath("encrypted")
        database.mkdirs()
        database.delete()
        val secureDB = SQLiteDatabase.openOrCreateDatabase(database, stringFromJNI(), null)
        secureDB.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR,Password VARCHAR);")
        secureDB.execSQL("INSERT INTO Accounts VALUES('admin','AdminPassEnc');")
        secureDB.close()
    }
}
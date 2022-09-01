package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View

class OMTG_DATAST_001_SQLite_Not_Encrypted : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__sqlite)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        SQLiteUnsafe()
    }

    private fun SQLiteUnsafe() {
        val notSoSecure = openOrCreateDatabase("privateNotSoSecure", Context.MODE_PRIVATE, null)
        notSoSecure.execSQL("CREATE TABLE IF NOT EXISTS Accounts(Username VARCHAR,Password VARCHAR);")
        notSoSecure.execSQL("INSERT INTO Accounts VALUES('admin','AdminPass');")
        notSoSecure.close()
    }
}
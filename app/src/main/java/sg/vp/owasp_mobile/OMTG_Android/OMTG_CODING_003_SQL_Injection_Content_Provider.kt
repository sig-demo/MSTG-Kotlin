package sg.vp.owasp_mobile.OMTG_Android

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.Toast

class OMTG_CODING_003_SQL_Injection_Content_Provider : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__coding_003__sql__injection__content__provider)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_my, menu)
        return true
    }

    fun onClickAddName(view: View?) { // Add a new student record
        val values = ContentValues()
        values.put(OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation.NAME,
                (findViewById<View>(R.id.editText2) as EditText).text.toString())
        values.put(OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation.GRADE,
                (findViewById<View>(R.id.editText3) as EditText).text.toString())
        val uri = contentResolver.insert(
                OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation.CONTENT_URI, values)
        Toast.makeText(baseContext,
                uri.toString(), Toast.LENGTH_LONG).show()
    }

    fun onClickRetrieveStudents(view: View?) { // Retrieve student records
        val URL = "content://sg.vp.owasp_mobile.provider.College/students"
        val searchPattern = findViewById<View>(R.id.searchPattern) as EditText
        // SQL Injecdtion possible
// BOB') OR 1=1--
        Log.e("searchPattern", searchPattern.text.toString())
        var WHERE: String? = null
        if (searchPattern.text.toString() != null && !searchPattern.text.toString().isEmpty()) {
            WHERE = "name='" + searchPattern.text.toString() + "'"
        }
        val students = Uri.parse(URL)
        val c = managedQuery(students, null, WHERE, null, "name")
        if (c.moveToFirst()) {
            do {
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation._ID)) +
                                ", " + c.getString(c.getColumnIndex(OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation.NAME)) +
                                ", " + c.getString(c.getColumnIndex(OMTG_CODING_003_SQL_Injection_Content_Provider_Implementation.GRADE)),
                        Toast.LENGTH_SHORT).show()
            } while (c.moveToNext())
        }
    }
}
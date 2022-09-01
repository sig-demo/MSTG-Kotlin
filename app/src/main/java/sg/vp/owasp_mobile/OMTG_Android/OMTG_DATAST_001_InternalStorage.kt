package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class OMTG_DATAST_001_InternalStorage : AppCompatActivity() {
    // https://developer.android.com/guide/topics/data/data-storage.html#filesInternal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__internal_storage)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        try {
            writeFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun writeFile() {
        val FILENAME = "test_file"
        val string = "Credit Card Number is 1234 4321 5678 8765"
        var fos: FileOutputStream? = null
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        fos!!.write(string.toByteArray())
        fos.close()
    }
}
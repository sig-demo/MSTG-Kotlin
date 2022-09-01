package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class OMTG_DATAST_001_ExternalStorage : AppCompatActivity() {
    // https://developer.android.com/guide/topics/data/data-storage.html#filesExternal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_001__external_storage)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        if (isExternalStorageWritable) {
            val file = File(Environment.getExternalStorageDirectory(), "password.txt")
            //            Log.d("External Storage Directory", String.valueOf(Environment.getExternalStorageDirectory()));
            val password = "L33tS3cr3t"
            val fos: FileOutputStream
            try {
                fos = FileOutputStream(file)
                fos.write(password.toByteArray())
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /* Checks if external storage is available for read and write */
    val isExternalStorageWritable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state) {
                true
            } else false
        }

    /* Checks if external storage is available to at least read */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
                true
            } else false
        }
}
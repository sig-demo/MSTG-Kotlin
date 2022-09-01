package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.util.Log
import android.view.View
import dalvik.system.DexClassLoader

// Sample from  http://stackoverflow.com/questions/6857807/is-it-possible-to-dynamically-load-a-library-at-runtime-from-an-android-applicat
class OMTG_CODING_004_Code_Injection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__coding_004__code__injection)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        try {
            val libPath = Environment.getExternalStorageDirectory().toString() + "/libcodeinjection.jar"
            val tmpDir = getDir("dex", 0)
            //            Log.e("Directory getExternalStorageDirectory: ", Environment.getExternalStorageDirectory().toString());
            val classloader = DexClassLoader(libPath, tmpDir.absolutePath, null, this.javaClass.classLoader)
            val classToLoad = classloader.loadClass("com.example.CodeInjection") as Class<Any>
            //            Log.e("Directory tmpDir: ", tmpDir.getAbsolutePath().toString());
            val myInstance = classToLoad.newInstance()
            val returnString = classToLoad.getMethod("returnString")
            val result = returnString.invoke(myInstance) as String
            Log.e("Test", result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
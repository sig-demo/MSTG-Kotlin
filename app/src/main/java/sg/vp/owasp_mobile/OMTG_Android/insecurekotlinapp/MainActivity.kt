package sg.vp.owasp_mobile.OMTG_Android.insecurekotlinapp

import android.os.Bundle
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sg.vp.owasp_mobile.OMTG_Android.R
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var retrofit: Retrofit? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_insecurekotlinapp)
        retrofit = Retrofit.Builder()
                .baseUrl("http://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val loginbutton1 = findViewById<Button>(R.id.loginButton1)
        loginbutton1.setOnClickListener {
            val username = obtainField(R.id.username)
            val password = obtainField(R.id.password)
            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    sendData(username, password)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun obtainField(id: Int): String {
        val textInputLayout = findViewById<TextInputLayout>(id)
        val text = textInputLayout.editText!!.text.toString()
        textInputLayout.isErrorEnabled = text.isEmpty()
        return if (text.isEmpty()) {
            textInputLayout.error = "Поле не может быть пустым"
            ""
        } else {
            textInputLayout.error = ""
            text
        }
    }

    @Throws(IOException::class)
    private fun sendData(username: String, password: String) {
        val service = retrofit!!.create(InsecureService::class.java)
        var logincall: Call<Response?>? = null
        logincall = service.login(Model(username, password))
        logincall!!.enqueue(object : Callback<Response?> {
            override fun onResponse(call: Call<Response?>, response: retrofit2.Response<Response?>) {
                if (response.isSuccessful) {
                    val tx = findViewById<TextView>(R.id.responseText)
                    tx.text = response.body().toString()
                    Log.i("ResponseBody", response.body().toString())
                } else {
                    Log.d("Error", "response not successful")
                }
            }

            override fun onFailure(call: Call<Response?>, t: Throwable) {
                Log.d("Error", t.message)
            }
        })
    }
}
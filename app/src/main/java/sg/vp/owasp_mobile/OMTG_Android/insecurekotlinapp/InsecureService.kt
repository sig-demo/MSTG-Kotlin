package sg.vp.owasp_mobile.OMTG_Android.insecurekotlinapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

internal interface InsecureService {
    @POST("post")
    fun login(@Body model: Model?): Call<Response?>?
}
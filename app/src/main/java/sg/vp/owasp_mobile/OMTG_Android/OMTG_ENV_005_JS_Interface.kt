package sg.vp.owasp_mobile.OMTG_Android

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

/**
 * Created by sven on 22/6/16.
 */
class OMTG_ENV_005_JS_Interface {
    var mContext: Context? = null

    /** Instantiate the interface and set the context  */
    internal constructor(c: Context?) {
        mContext = c
    }

    constructor() {}

    @JavascriptInterface
    fun returnString(): String {
        return "Secret String"
    }

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun showToast(toast: String?) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
    }
}
package sg.vp.owasp_mobile.OMTG_Android

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.EditText

class OMTG_DATAST_006_Clipboard : AppCompatActivity() {
    var sensitiveInformation: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omtg__datast_006__clipboard)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        sensitiveInformation = findViewById<View>(R.id.ClipboardEditText) as EditText
        // NOT WORKING ATM
//
//        sensitiveInformation.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
//
//            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            public void onDestroyActionMode(ActionMode mode) {
//            }
//
//            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                return false;
//            }
//
//            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                return false;
//            }
//        });
    } //    @Override
//    protected void onCreateContextMenu(ContextMenu menu)
//    {
//        //super.onCreateContextMenu(menu); //Be sure to comment this line or remove it completely.
//        // menu.clear();
//    }
}
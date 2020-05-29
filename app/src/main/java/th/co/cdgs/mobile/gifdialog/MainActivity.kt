package th.co.cdgs.mobile.gifdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dialog: GifDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = GifDialog.with(this)
        dialog!!.isCancelable(true)
            .setResourceId(R.drawable.giphy5)
            .setWidth(100)
            .setHeight(100)
            .showDialog()

        btnRecreate.setOnClickListener{
            if(dialog?.isShowing()!!) {
                dialog?.dismissDialog()
            } else {
                dialog?.showDialog()
            }
        }
    }
}
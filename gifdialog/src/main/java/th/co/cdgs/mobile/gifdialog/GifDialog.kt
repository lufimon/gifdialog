package th.co.cdgs.mobile.gifdialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView

class GifDialog private constructor(private var ctx: Context) {

    private var mDialog: Dialog? = null
    private var map: HashMap<String, Dialog>? = null

    private var resourceId: Int? = null
    private var dialogBackgroundResource: Int
    private var isCancelable: Boolean
    private var dimAmount: Float
    private var width: Int
    private var height: Int

    init {
        this.isCancelable = true
        this.dialogBackgroundResource = android.R.color.transparent
        this.width = 100
        this.height = 100
        dimAmount = 0.5f
        map = HashMap()
    }

    companion object {
        fun with(context: Context): GifDialog {
            if (!Fresco.hasBeenInitialized())
                Fresco.initialize(context)
            return GifDialog(context)
        }
    }

    fun setWidth(value: Int): GifDialog {
        width = value
        return this
    }

    fun setHeight(value: Int): GifDialog {
        height = value
        return this
    }

    fun setResourceId(id: Int): GifDialog {
        resourceId = id
        return this
    }

    fun isCancelable(value: Boolean): GifDialog {
        isCancelable = value
        return this
    }

    fun setDimAmount(value: Float): GifDialog {
        dimAmount = value
        return this
    }

    fun setDialogBackgroundResource(id: Int): GifDialog {
        dialogBackgroundResource = id
        return this
    }

    fun isShowing(): Boolean? {
        return mDialog?.isShowing
    }

    fun showDialog(tag: String = "gifdialog") {
        // Remove redundant dialogs
        if (map?.containsKey(tag) != null) {
            map?.get(tag)?.dismiss()
            map?.remove(tag)
        }

        // Create a new dialog
        mDialog = Dialog(ctx)
        mDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog?.setContentView(R.layout.gif_dialog)
        mDialog?.window?.setBackgroundDrawableResource(dialogBackgroundResource)
        mDialog?.window?.setDimAmount(dimAmount)
        mDialog?.setCancelable(isCancelable)

        val draweeView = mDialog?.findViewById<SimpleDraweeView>(R.id.draweeView)

        draweeView?.aspectRatio = width.toFloat() / height.toFloat()
        draweeView?.layoutParams = ConstraintLayout.LayoutParams(width, height)

        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
            .setUri(UriUtil.getUriForResourceId(resourceId!!))
            .setAutoPlayAnimations(true)
            .build()
        draweeView?.controller = controller

        map?.put(tag, mDialog!!)
        mDialog?.show()
    }

    fun dismissDialog(tag: String = "gifdialog") {
        val d = map?.get(tag)

        if (d != null) {
            d.dismiss()
            map?.remove(tag)
        }
    }
}
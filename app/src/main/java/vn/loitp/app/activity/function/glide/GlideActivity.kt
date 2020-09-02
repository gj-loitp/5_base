package vn.loitp.app.activity.function.glide

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil.Companion.loadNoAmin
import kotlinx.android.synthetic.main.activity_func_glide.*
import vn.loitp.app.R

class GlideActivity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt0.setOnClickListener(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_glide
    }

    private val urlLow = "https://c1.staticflickr.com/5/4740/39507376464_359b870746_z.jpg"
    private val urlMedium = "https://c1.staticflickr.com/5/4740/39507376464_e00b5e16d7_h.jpg"
    private val urlHigh = "https://c1.staticflickr.com/5/4740/39507376464_c632938112_o.jpg"

    override fun onClick(v: View) {
        when (v) {
            bt0 -> loadNoAmin(context = activity, url = urlLow, urlThumbnal = urlLow, imageView = imageView, drawableRequestListener = null)
            bt1 -> loadNoAmin(context = activity, url = urlMedium, urlThumbnal = urlLow, imageView = imageView, drawableRequestListener = null)
            bt2 -> loadNoAmin(context = activity, url = urlHigh, urlThumbnal = urlLow, imageView = imageView, drawableRequestListener = null)
        }
    }
}

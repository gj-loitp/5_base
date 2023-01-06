package vn.loitp.a.func.glide

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.loadGlide
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_func_glide.*
import vn.loitp.R

@LogTag("GlideActivity")
@IsFullScreen(false)
class GlideActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_func_glide
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GlideActivityFont::class.java.simpleName
        }
        bt0.setOnClickListener(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
    }

    private val urlLow = "https://c1.staticflickr.com/5/4740/39507376464_359b870746_z.jpg"
    private val urlMedium = "https://c1.staticflickr.com/5/4740/39507376464_e00b5e16d7_h.jpg"
    private val urlHigh = "https://c1.staticflickr.com/5/4740/39507376464_c632938112_o.jpg"

    override fun onClick(v: View) {
        when (v) {
            bt0 -> imageView.loadGlide(
                any = urlLow,
                drawableRequestListener = null
            )
            bt1 -> imageView.loadGlide(
                any = urlMedium,
                drawableRequestListener = null
            )
            bt2 -> imageView.loadGlide(
                any = urlHigh,
                drawableRequestListener = null
            )
        }
    }
}

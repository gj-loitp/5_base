package vn.loitp.app.activity.function.glide

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_func_glide.*
import vn.loitp.app.R

@LogTag("GlideActivity")
@IsFullScreen(false)
class GlideActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_glide
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
            this.tvTitle?.text = GlideActivity::class.java.simpleName
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
            bt0 -> LImageUtil.load(
                context = this,
                any = urlLow,
                imageView = imageView,
                drawableRequestListener = null
            )
            bt1 -> LImageUtil.load(
                context = this,
                any = urlMedium,
                imageView = imageView,
                drawableRequestListener = null
            )
            bt2 -> LImageUtil.load(
                context = this,
                any = urlHigh,
                imageView = imageView,
                drawableRequestListener = null
            )
        }
    }
}

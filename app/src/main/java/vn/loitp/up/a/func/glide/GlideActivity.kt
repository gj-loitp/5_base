package vn.loitp.up.a.func.glide

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AFuncGlideBinding

@LogTag("GlideActivity")
@IsFullScreen(false)
class GlideActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AFuncGlideBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncGlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GlideActivity::class.java.simpleName
        }
        binding.bt0.setOnClickListener(this)
        binding.bt1.setOnClickListener(this)
        binding.bt2.setOnClickListener(this)
    }

    private val urlLow = "https://c1.staticflickr.com/5/4740/39507376464_359b870746_z.jpg"
    private val urlMedium = "https://c1.staticflickr.com/5/4740/39507376464_e00b5e16d7_h.jpg"
    private val urlHigh = "https://c1.staticflickr.com/5/4740/39507376464_c632938112_o.jpg"

    override fun onClick(v: View) {
        when (v) {
            binding.bt0 -> binding.imageView.loadGlide(
                any = urlLow,
                drawableRequestListener = null
            )
            binding.bt1 -> binding.imageView.loadGlide(
                any = urlMedium,
                drawableRequestListener = null
            )
            binding.bt2 -> binding.imageView.loadGlide(
                any = urlHigh,
                drawableRequestListener = null
            )
        }
    }
}

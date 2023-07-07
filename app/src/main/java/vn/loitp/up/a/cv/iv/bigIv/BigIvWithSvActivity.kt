package vn.loitp.up.a.cv.iv.bigIv

import android.net.Uri
import android.os.Bundle
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ABigIvWithSvBinding
import vn.loitp.up.common.Constants

// https://github.com/Piasy/BigImageViewer
@LogTag("BigImageViewWithScrollViewActivity")
@IsFullScreen(false)
class BigIvWithSvActivity : BaseActivityFont() {
    private lateinit var binding: ABigIvWithSvBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABigIvWithSvBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = BigIvWithSvActivity::class.java.simpleName
        }

        binding.biv0.setImageViewFactory(GlideImageViewFactory())
        binding.biv1.setImageViewFactory(GlideImageViewFactory())
        binding.biv2.setImageViewFactory(GlideImageViewFactory())
        binding.biv3.setImageViewFactory(GlideImageViewFactory())

        binding.biv0.showImage(
            Uri.parse(Constants.URL_IMG_LARGE_LAND_S),
            Uri.parse(Constants.URL_IMG_LARGE_LAND_O)
        )
        binding.biv1.showImage(Uri.parse(Constants.URL_IMG_LONG))
        binding.biv2.showImage(Uri.parse(Constants.URL_IMG_GIFT))
        binding.biv3.showImage(
            Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_S),
            Uri.parse(Constants.URL_IMG_LARGE_PORTRAIT_O)
        )
    }
}

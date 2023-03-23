package vn.loitp.up.a.cv.dlg.slideImages

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.common.URL_IMG_2
import com.loitp.core.common.URL_IMG_3
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showDialogSlide
import vn.loitp.R
import vn.loitp.databinding.ADlgSlideImagesBinding

@LogTag("DialogSlideImagesActivity")
@IsFullScreen(false)
class DialogSlideImagesActivity : BaseActivityFont() {
    private lateinit var binding: ADlgSlideImagesBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADlgSlideImagesBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = DialogSlideImagesActivity::class.java.simpleName
        }

        val url0 = URL_IMG_1
        val url1 = URL_IMG_2
        val url2 = URL_IMG_3

        val imageList = ArrayList<String>()
        imageList.add(url0)
        imageList.add(url1)
        imageList.add(url2)

        binding.iv0.loadGlide(any = url0)
        binding.iv1.loadGlide(
            any = url1,
        )
        binding.iv2.loadGlide(
            any = url2,
        )

        binding.iv0.setSafeOnClickListener {
            this.showDialogSlide(
                index = 0,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        binding.iv1.setSafeOnClickListener {
            this.showDialogSlide(
                index = 1,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        binding.iv2.setSafeOnClickListener {
            this.showDialogSlide(
                index = 2,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
    }
}

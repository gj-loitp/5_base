package vn.loitp.a.cv.dlg.slideImages

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.common.URL_IMG_2
import com.loitp.core.common.URL_IMG_3
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showDialogSlide
import kotlinx.android.synthetic.main.a_dlg_slide_images.*
import vn.loitp.R

@LogTag("DialogSlideImagesActivity")
@IsFullScreen(false)
class DialogSlideImagesActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_dlg_slide_images
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = DialogSlideImagesActivityFont::class.java.simpleName
        }

        val url0 = URL_IMG_1
        val url1 = URL_IMG_2
        val url2 = URL_IMG_3

        val imageList = ArrayList<String>()
        imageList.add(url0)
        imageList.add(url1)
        imageList.add(url2)

        iv0.loadGlide(any = url0)
        iv1.loadGlide(
            any = url1,
        )
        iv2.loadGlide(
            any = url2,
        )

        iv0.setSafeOnClickListener {
            this.showDialogSlide(
                index = 0,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        iv1.setSafeOnClickListener {
            this.showDialogSlide(
                index = 1,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        iv2.setSafeOnClickListener {
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

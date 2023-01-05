package vn.loitp.a.cv.dlg.slideImages

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = DialogSlideImagesActivityFont::class.java.simpleName
        }

        val url0 = Constants.URL_IMG_1
        val url1 = Constants.URL_IMG_2
        val url2 = Constants.URL_IMG_3

        val imageList = ArrayList<String>()
        imageList.add(url0)
        imageList.add(url1)
        imageList.add(url2)

        LImageUtil.load(context = this, any = url0, imageView = iv0)
        LImageUtil.load(context = this, any = url1, imageView = iv1)
        LImageUtil.load(context = this, any = url2, imageView = iv2)

        iv0.setSafeOnClickListener {
            LDialogUtil.showDialogSlide(
                context = this,
                index = 0,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        iv1.setSafeOnClickListener {
            LDialogUtil.showDialogSlide(
                context = this,
                index = 1,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
        iv2.setSafeOnClickListener {
            LDialogUtil.showDialogSlide(
                context = this,
                index = 2,
                imgList = imageList,
                amount = 0.5f,
                isShowController = true,
                isShowIconClose = true
            )
        }
    }
}

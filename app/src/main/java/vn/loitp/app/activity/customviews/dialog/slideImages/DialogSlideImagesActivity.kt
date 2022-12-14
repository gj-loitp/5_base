package vn.loitp.app.activity.customviews.dialog.slideImages

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_dialog_slide_images.*
import vn.loitp.app.R

@LogTag("DialogSlideImagesActivity")
@IsFullScreen(false)
class DialogSlideImagesActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_slide_images
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
            this.tvTitle?.text = DialogSlideImagesActivity::class.java.simpleName
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

package com.loitpcore.core.helper.gallery.slide

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitpcore.R
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsShowAdWhenExit
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LValidateUtil
import com.loitpcore.function.pump.download.Pump
import com.loitpcore.function.pump.download.core.DownloadListener
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_slide.*

@LogTag("GalleryCoreSlideActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
class GalleryCoreSlideActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_slide
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = slidePagerAdapter
//        LUIUtil.setPullLikeIOSHorizontal(viewPager)
//        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        val photoID = intent.getStringExtra(Constants.SK_PHOTO_ID) ?: ""
        val position = instance.getPosition(photoID)

        viewPager.currentItem = position

        btDownload.setOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                save(url = it)
            }
        }
        btShare.setOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                LSocialUtil.share(activity = this, msg = it)
            }
        }
        btReport.setOnClickListener {
            LSocialUtil.sendEmail(context = this)
        }

        LValidateUtil.isValidPackageName()
    }

    private inner class SlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {

            val frmIvSlideCore = FrmIvSlideCore()
            val bundle = Bundle()
            bundle.putInt(Constants.SK_PHOTO_PISITION, position)
            frmIvSlideCore.arguments = bundle
            return frmIvSlideCore
        }

        override fun getCount(): Int {
            return instance.size
        }
    }

//    fun toggleDisplayRlControl() {
//        if (isRlControlShowing) {
//            hideRlControl()
//        } else {
//            showRlControl()
//        }
//    }
//
//    private var isRlControlShowing = true
//    private fun showRlControl() {
//        rlControl.visibility = View.VISIBLE
//        isRlControlShowing = true
//        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideInUp)
//    }
//
//    private fun hideRlControl() {
//        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideOutDown, callbackAnimation = object : CallbackAnimation {
//            override fun onCancel() {}
//            override fun onEnd() {
//                rlControl.visibility = View.INVISIBLE
//                isRlControlShowing = false
//            }
//
//            override fun onRepeat() {}
//            override fun onStart() {}
//        })
//    }

    private fun save(url: String) {
        Pump.newRequestToPicture(url, "/loitp/picture")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                }

                override fun onFailed() {
                    showShortError("Download failed")
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }
}

package com.core.helper.gallery.slide

import alirezat775.lib.downloader.core.OnDownloadListener
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LAppResource
import com.core.utilities.LSocialUtil
import com.core.utilities.LStoreUtil
import com.core.utilities.LValidateUtil
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_slide.*
import java.io.File

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
        val downloader = LStoreUtil.getDownloader(
            folderName = Environment.DIRECTORY_PICTURES + "/" + LAppResource.getString(R.string.app_name),
            url = url,
            onDownloadListener = object : OnDownloadListener {
                override fun onCancel() {
                }

                override fun onCompleted(file: File?) {
                    file?.let {
                        showLongInformation("Saved in ${it.path}")
                        LStoreUtil.sendBroadcastMediaScan(it)
                    }
                }

                override fun onFailure(reason: String?) {
                    showLongError("Download failed $reason")
                }

                override fun onPause() {
                }

                override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                }

                override fun onResume() {
                }

                override fun onStart() {
                }

            }
        )
        downloader.download()
    }
}

package com.core.helper.gallery.slide

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LAnimationUtil
import com.core.utilities.LSocialUtil
import com.daimajia.androidanimations.library.Techniques
import com.interfaces.CallbackAnimation
import com.task.AsyncTaskDownloadImage
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_slide.*

@LogTag("GalleryCoreSlideActivity")
@IsFullScreen(false)
class GalleryCoreSlideActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_flickr_gallery_core_slide)

        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = slidePagerAdapter
//        LUIUtil.setPullLikeIOSHorizontal(viewPager)
//        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        val photoID = intent.getStringExtra(Constants.SK_PHOTO_ID) ?: ""
        val position = instance.getPosition(photoID)

        viewPager.currentItem = position

        btDownload.setOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                AsyncTaskDownloadImage(applicationContext, it).execute()
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
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
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

    fun toggleDisplayRlControl() {
        if (isRlControlShowing) {
            hideRlControl()
        } else {
            showRlControl()
        }
    }

    private var isRlControlShowing = true
    private fun showRlControl() {
        rlControl.visibility = View.VISIBLE
        isRlControlShowing = true
        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideInUp)
    }

    private fun hideRlControl() {
        LAnimationUtil.play(view = rlControl, techniques = Techniques.SlideOutDown, callbackAnimation = object : CallbackAnimation {
            override fun onCancel() {}
            override fun onEnd() {
                rlControl.visibility = View.INVISIBLE
                isRlControlShowing = false
            }

            override fun onRepeat() {}
            override fun onStart() {}
        })
    }
}

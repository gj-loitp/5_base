package com.loitp.core.helper.gallery.slide

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.huxq17.download.Pump
import com.huxq17.download.core.DownloadListener
import com.loitpcore.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitp.core.utilities.LSocialUtil
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_slide.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryCoreSlideActivity")
@IsFullScreen(false)
class GalleryCoreSlideActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_slide
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = slidePagerAdapter
//        LUIUtil.setPullLikeIOSHorizontal(viewPager)
//        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        val photoID = intent.getStringExtra(Constants.SK_PHOTO_ID) ?: ""
        val position = instance.getPosition(photoID)

        viewPager.currentItem = position

        btDownload.setSafeOnClickListener {
            instance.getPhoto(position = viewPager.currentItem)?.urlO?.let {
                save(url = it)
            }
        }
        btShare.setSafeOnClickListener {
            instance.getPhoto(viewPager.currentItem)?.urlO?.let {
                LSocialUtil.share(activity = this, msg = it)
            }
        }
        btReport.setSafeOnClickListener {
            LSocialUtil.sendEmail(context = this)
        }
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

    private fun save(url: String) {
        Pump.newRequestToPicture(url, "/roygroup/picture")
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

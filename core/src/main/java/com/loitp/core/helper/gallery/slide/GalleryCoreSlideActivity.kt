package com.loitp.core.helper.gallery.slide

import android.os.Bundle
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.huxq17.download.Pump
import com.huxq17.download.core.DownloadListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.SK_PHOTO_ID
import com.loitp.core.common.SK_PHOTO_PISITION
import com.loitp.core.ext.sendEmail
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.share
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitp.databinding.LAFlickrGalleryCoreSlideBinding

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@LogTag("GalleryCoreSlideActivity")
@IsFullScreen(false)
class GalleryCoreSlideActivity : BaseActivityFont() {

    private lateinit var binding: LAFlickrGalleryCoreSlideBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFlickrGalleryCoreSlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = slidePagerAdapter
//        LUIUtil.setPullLikeIOSHorizontal(viewPager)
//        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        val photoID = intent.getStringExtra(SK_PHOTO_ID) ?: ""
        val position = instance.getPosition(photoID)

        binding.viewPager.currentItem = position

        binding.btDownload.setSafeOnClickListener {
            instance.getPhoto(position = binding.viewPager.currentItem)?.urlO?.let {
                save(url = it)
            }
        }
        binding.btShare.setSafeOnClickListener {
            instance.getPhoto(binding.viewPager.currentItem)?.urlO?.let {
                this.share(msg = it)
            }
        }
        binding.btReport.setSafeOnClickListener {
            this.sendEmail()
        }
    }

    private inner class SlidePagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {

            val frmIvSlideCore = FrmIvSlideCore()
            val bundle = Bundle()
            bundle.putInt(SK_PHOTO_PISITION, position)
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

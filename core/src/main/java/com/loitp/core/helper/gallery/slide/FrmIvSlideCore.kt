package com.loitp.core.helper.gallery.slide

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.R
import com.loitp.core.common.Constants
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.setTextShadow
import com.loitp.core.ext.showProgress
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import kotlinx.android.synthetic.main.l_i_flickr_photo_slide_iv_core.*
import java.io.File

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class FrmIvSlideCore : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.l_i_flickr_photo_slide_iv_core, container, false)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val bundle = arguments ?: return
        val position = bundle.getInt(Constants.SK_PHOTO_PISITION)
        val photo = instance.getPhoto(position)

        tvProgress.setTextShadow(color = null)
        biv.setImageViewFactory(GlideImageViewFactory())

        biv.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(
                imageType: Int,
                image: File
            ) {
            }

            override fun onCacheMiss(
                imageType: Int,
                image: File
            ) {
            }

            override fun onStart() {
                progressBar.showProgress()
                tvProgress.text = "0%"
            }

            @SuppressLint("SetTextI18n")
            override fun onProgress(progress: Int) {
                tvProgress.visibility = View.VISIBLE
                tvProgress.text = "$progress%"
            }

            override fun onFinish() {}
            override fun onSuccess(image: File) {
                progressBar.hideProgress()
                tvProgress.visibility = View.GONE
            }

            override fun onFail(error: Exception) {}
        })
        biv.showImage(Uri.parse(photo?.urlS), Uri.parse(photo?.urlO))
    }
}

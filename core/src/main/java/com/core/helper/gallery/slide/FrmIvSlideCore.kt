package com.core.helper.gallery.slide

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.R
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.core.utilities.LUIUtil
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import kotlinx.android.synthetic.main.l_item_flickr_photo_slide_iv_core.*
import java.io.File

class FrmIvSlideCore : Fragment() {
    private val logTag = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.l_item_flickr_photo_slide_iv_core, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: return
        val position = bundle.getInt(Constants.SK_PHOTO_PISITION)
        val photo = instance.getPhoto(position)

        LUIUtil.setTextShadow(textView = tvProgress)
        biv.setImageViewFactory(GlideImageViewFactory())
        
        biv.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(imageType: Int, image: File) {}
            override fun onCacheMiss(imageType: Int, image: File) {}
            override fun onStart() {
                indicatorView.smoothToShow()
                tvProgress.text = "0%"
            }

            @SuppressLint("SetTextI18n")
            override fun onProgress(progress: Int) {
                tvProgress.visibility = View.VISIBLE
                tvProgress.text = "$progress%"
            }

            override fun onFinish() {}
            override fun onSuccess(image: File) {
                indicatorView.smoothToHide()
                tvProgress.visibility = View.GONE
            }

            override fun onFail(error: Exception) {}
        })
        biv.showImage(Uri.parse(photo?.urlS), Uri.parse(photo?.urlO))
        biv.setOnClickListener {
            if (activity == null) {
                return@setOnClickListener
            }
            (activity as GalleryCoreSlideActivity).toggleDisplayRlControl()
        }
    }
}

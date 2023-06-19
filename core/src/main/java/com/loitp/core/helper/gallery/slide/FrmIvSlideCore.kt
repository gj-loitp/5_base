package com.loitp.core.helper.gallery.slide

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.github.piasy.biv.loader.ImageLoader
import com.github.piasy.biv.view.GlideImageViewFactory
import com.loitp.core.common.SK_PHOTO_PISITION
import com.loitp.core.ext.hideProgress
import com.loitp.core.ext.setTextShadow
import com.loitp.core.ext.showProgress
import com.loitp.core.helper.gallery.photos.PhotosDataCore.Companion.instance
import com.loitp.databinding.LIFlickrPhotoSlideIvCoreBinding
import java.io.File

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class FrmIvSlideCore : Fragment() {
    private lateinit var binding: LIFlickrPhotoSlideIvCoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = LIFlickrPhotoSlideIvCoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val bundle = arguments ?: return
        val position = bundle.getInt(SK_PHOTO_PISITION)
        val photo = instance.getPhoto(position)

        binding.tvProgress.setTextShadow(color = null)
        binding.biv.setImageViewFactory(GlideImageViewFactory())

        binding.biv.setImageLoaderCallback(object : ImageLoader.Callback {
            override fun onCacheHit(
                imageType: Int, image: File
            ) {
            }

            override fun onCacheMiss(
                imageType: Int, image: File
            ) {
            }

            override fun onStart() {
                binding.progressBar.showProgress()
                binding.tvProgress.text = "0%"
            }

            @SuppressLint("SetTextI18n")
            override fun onProgress(progress: Int) {
                binding.tvProgress.visibility = View.VISIBLE
                binding.tvProgress.text = "$progress%"
            }

            override fun onFinish() {}
            override fun onSuccess(image: File) {
                binding.progressBar.hideProgress()
                binding.tvProgress.visibility = View.GONE
            }

            override fun onFail(error: Exception) {}
        })
        binding.biv.showImage(Uri.parse(photo?.urlS), Uri.parse(photo?.urlO))
    }
}

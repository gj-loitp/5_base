package com.loitp.core.helper.gallery.member

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.annotation.Keep
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getSerializableCompat
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setZoomFitWidthScreen
import com.loitp.core.ext.transActivityNoAnimation
import com.loitp.databinding.LAFlickrMemberDetailBinding
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@LogTag("GalleryMemberDetailActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class GalleryMemberDetailActivity : BaseActivityFont() {

    companion object {
        const val PHOTO = "PHOTO"
    }

    private lateinit var binding: LAFlickrMemberDetailBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LAFlickrMemberDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val photo = intent?.extras?.getSerializableCompat(PHOTO, Photo::class.java)
        photo?.let {
            loadItem(photo = it)
        }

        binding.swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?, swipeBackFraction: Float, swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(
                mView: View?, isEnd: Boolean
            ) {
                if (isEnd) {
                    finish()//correct
                    this@GalleryMemberDetailActivity.transActivityNoAnimation()
                }
            }
        })
    }

    private fun loadItem(photo: Photo) {
        binding.tvTitle.text = photo.title
        binding.imageViewBlur.loadGlide(
            any = photo.urlS,
            drawableRequestListener = null,
            transformation = BlurTransformation(25)
        )
        binding.imageView.loadGlide(
            any = photo.urlO,
            drawableRequestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.imageView.setZoomFitWidthScreen()
                    return false
                }
            })
    }
}

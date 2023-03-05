package com.loitp.core.helper.gallery.member

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitp.R
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.getSerializableCompat
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setZoomFitWidthScreen
import com.loitp.core.ext.transActivityNoAnimation
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitp.views.layout.swipeBack.SwipeBackLayout
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.l_a_flickr_member_detail.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GalleryMemberDetailActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class GalleryMemberDetailActivity : BaseActivityFont() {

    companion object {
        const val PHOTO = "PHOTO"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_a_flickr_member_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val photo = intent?.extras?.getSerializableCompat(PHOTO, Photo::class.java)
        photo?.let {
            loadItem(photo = it)
        }

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
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
        tvTitle.text = photo.title
        imageViewBlur.loadGlide(
            any = photo.urlS,
            drawableRequestListener = null,
            transformation = BlurTransformation(25)
        )
        imageView.loadGlide(
            any = photo.urlO,
            drawableRequestListener = object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setZoomFitWidthScreen()
                    return false
                }
            })
    }
}

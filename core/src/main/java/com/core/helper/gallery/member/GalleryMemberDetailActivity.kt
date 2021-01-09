package com.core.helper.gallery.member

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.base.BaseFontActivity
import com.core.utilities.*
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.views.layout.swipeback.SwipeBackLayout
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.l_activity_flickr_member_detail.*

@LogTag("GalleryMemberDetailActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class GalleryMemberDetailActivity : BaseFontActivity() {

    companion object {
        const val PHOTO = "PHOTO"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_member_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo = photo)

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View?, swipeBackFraction: Float, swipeBackFactor: Float) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryMemberDetailActivity)
                }
            }
        })

        LValidateUtil.isValidPackageName()
    }

    private fun loadItem(photo: Photo) {
        tvTitle.text = photo.title
        LImageUtil.load(
                context = this,
                any = photo.urlS,
                imageView = imageViewBlur,
                drawableRequestListener = null,
                transformation = BlurTransformation(25)
        )
        LImageUtil.load(
                context = this,
                any = photo.urlO,
                imageView = imageView,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        LImageUtil.setZoomFitWidthScreen(touchImageView = imageView)
                        return false
                    }
                }
        )
    }
}

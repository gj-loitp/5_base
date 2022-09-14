package com.loitpcore.core.helper.gallery.member

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.loitpcore.R
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.IsSwipeActivity
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.restApi.flickr.model.photoSetGetPhotos.Photo
import com.loitpcore.views.layout.swipeBack.SwipeBackLayout
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.l_activity_flickr_member_detail.*

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
        //TODO fix getSerializableExtra
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo = photo)

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(
                mView: View?,
                swipeBackFraction: Float,
                swipeBackFactor: Float
            ) {
            }

            override fun onViewSwipeFinished(mView: View?, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryMemberDetailActivity)
                }
            }
        })
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
                    LImageUtil.setZoomFitWidthScreen(touchImageView = imageView)
                    return false
                }
            }
        )
    }
}

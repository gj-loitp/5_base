package com.core.helper.mup.comic.ui.activity

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
import com.core.helper.mup.comic.model.ChapterComicsDetail
import com.core.utilities.LActivityUtil
import com.core.utilities.LDeviceUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import com.views.layout.swipeback.SwipeBackLayout
import kotlinx.android.synthetic.main.l_activity_comic_read.swipeBackLayout
import kotlinx.android.synthetic.main.l_activity_viewer.*

@LogTag("ViewerActivity")
@IsFullScreen(true)
@IsSwipeActivity(true)
class ViewerActivity : BaseFontActivity() {

    companion object {
        const val KEY_DATA = "KEY_DATA"
    }

    private var chapterComicsDetail: ChapterComicsDetail? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_viewer
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        LScreenUtil.hideNavigationBar(this)
        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@ViewerActivity)
                }
            }
        })
    }

    private fun setupData() {
        intent.getSerializableExtra(KEY_DATA)?.let {
            if (it is ChapterComicsDetail) {
                chapterComicsDetail = it
            }
        }
        LImageUtil.loadHighQuality(
                any = chapterComicsDetail?.imageSrc,
                imageView = ivViewer,
                resPlaceHolder = R.color.transparent,
                resError = R.drawable.place_holder_error404,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                }
        )
    }

}

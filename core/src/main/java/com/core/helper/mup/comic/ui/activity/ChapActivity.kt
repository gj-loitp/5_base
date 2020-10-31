package com.core.helper.mup.comic.ui.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import com.R
import com.annotation.IsFullScreen
import com.annotation.IsShowAdWhenExit
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.base.BaseFontActivity
import com.core.helper.mup.comic.model.Comic
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.android.synthetic.main.l_activity_comic_chap.*

@LogTag("ComicActivity")
@IsFullScreen(false)
@IsShowAdWhenExit(true)
class ChapActivity : BaseFontActivity() {

    companion object {
        const val KEY_COMIC = "KEY_COMIC"
    }

    private var comic: Comic? = null
    private val color = if (LUIUtil.isDarkTheme()) {
        R.color.dark900
    } else {
        R.color.whiteSmoke
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_comic_chap)

        setupData()
        setupViews()
    }

    private fun setupData() {
        intent?.getSerializableExtra(KEY_COMIC)?.let {
            if (it is Comic) {
                comic = it
            }
        }
    }

    private fun setupViews() {
        toolbar.title = comic?.title
        LImageUtil.load(
                context = this,
                any = comic?.getImageSrc(),
                imageView = imgCover,
                resPlaceHolder = color,
                resError = color,
                transformation = BlurTransformation(25),
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
        LImageUtil.load(
                context = this,
                any = comic?.getImageSrc(),
                imageView = ivAvatar,
                resPlaceHolder = color,
                resError = color,
                transformation = CropCircleWithBorderTransformation(0, Color.TRANSPARENT),
                drawableRequestListener = null
        )
        fabLike.setSafeOnClickListener {
            //TODO loitpp iplm
            showLongInformation(getString(R.string.coming_soon))
        }
    }

}

package com.core.helper.mup.comic.ui.activity

import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
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
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.l_activity_comic_chap.*
import kotlinx.android.synthetic.main.l_item_flickr_album_core.view.*
import kotlin.math.roundToInt

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
        LImageUtil.load(context = this,
                url = comic?.getImageSrc(),
                imageView = imgCover,
                resPlaceHolder = color,
                resError = color,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })

    }

}

package com.loitpcore.views.autoSize.imageButtonWithSize

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.loitpcore.utils.util.ConvertUtils
import com.loitpcore.utils.util.ScreenUtils

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ImageButtonWithSize : AppCompatImageButton {
//    private val logTag = javaClass.simpleName

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val defaultSizePortrait = 50
    private val defaultSizeLandscape = 150
    private var portraitSizeW = defaultSizePortrait
    private var portraitSizeH = defaultSizePortrait
    private var landscapeSizeW = defaultSizeLandscape
    private var landscapeSizeH = defaultSizeLandscape

    fun setPortraitSizeWInPx(portraitSizeW: Int) {
        this.portraitSizeW = portraitSizeW
        updateSize()
    }

    fun setPortraitSizeHInPx(portraitSizeH: Int) {
        this.portraitSizeH = portraitSizeH
        updateSize()
    }

    fun setLandscapeSizeWInPx(landscapeSizeW: Int) {
        this.landscapeSizeW = landscapeSizeW
        updateSize()
    }

    fun setLandscapeSizeHInPx(landscapeSizeH: Int) {
        this.landscapeSizeH = landscapeSizeH
        updateSize()
    }

    fun setPortraitSizeWInDp(portraitSizeW: Float) {
        this.portraitSizeW = ConvertUtils.dp2px(portraitSizeW)
        updateSize()
    }

    fun setPortraitSizeHInDp(portraitSizeH: Float) {
        this.portraitSizeH = ConvertUtils.dp2px(portraitSizeH)
        updateSize()
    }

    fun setLandscapeSizeWInDp(landscapeSizeW: Float) {
        this.landscapeSizeW = ConvertUtils.dp2px(landscapeSizeW)
        updateSize()
    }

    fun setLandscapeSizeHInDp(landscapeSizeH: Float) {
        this.landscapeSizeH = ConvertUtils.dp2px(landscapeSizeH)
        updateSize()
    }

    private fun updateSize() {
        val isPortrait = ScreenUtils.isPortrait
        if (isPortrait) {
            this.layoutParams.width = portraitSizeW
            this.layoutParams.height = portraitSizeH
        } else {
            this.layoutParams.width = landscapeSizeW
            this.layoutParams.height = landscapeSizeH
        }
        invalidate()
    }

    public override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateSize()
    }
}

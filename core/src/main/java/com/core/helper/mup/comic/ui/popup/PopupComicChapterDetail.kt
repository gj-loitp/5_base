package com.core.helper.mup.comic.ui.popup

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.R
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.popup_comic_chapter_detail.view.*

@SuppressLint("InflateParams")
class PopupComicChapterDetail internal constructor(context: Context?) : RelativePopupWindow() {

    override fun showOnAnchor(
        anchor: View,
        vertPos: Int,
        horizPos: Int,
        x: Int,
        y: Int,
        fitInScreen: Boolean
    ) {
        super.showOnAnchor(anchor, vertPos, horizPos, x, y, fitInScreen)
    }

    var onClickShare: ((Unit) -> Unit)? = null
    var onClickDownload: ((Unit) -> Unit)? = null

    init {
        val layout = LayoutInflater.from(context).inflate(R.layout.popup_comic_chapter_detail, null)
        contentView = layout
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        isFocusable = true
        isOutsideTouchable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Disable default animation for circular reveal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animationStyle = 0
        }

        layout.btShare.setSafeOnClickListener {
            onClickShare?.invoke(Unit)
            dismiss()
        }

        layout.btDownload.setSafeOnClickListener {
            onClickDownload?.invoke(Unit)
            dismiss()
        }
    }
}

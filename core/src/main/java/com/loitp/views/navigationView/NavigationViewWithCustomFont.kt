package com.loitp.views.navigationView

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.CheckedTextView
import com.google.android.material.internal.NavigationMenuItemView
import com.google.android.material.navigation.NavigationView

/**
 * Created by Loitp on 24,October,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class NavigationViewWithCustomFont(c: Context, attrs: AttributeSet?) :
    NavigationView(
        c, attrs
    ) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (!isInEditMode) {
            val navMenuView = getChildAt(0) as ViewGroup?
            navMenuView?.let { vg ->
                val navMenuItemsCount = vg.childCount
//                if (fontFace == null) {
//                    fontFace = Typeface.createFromAsset(context.assets, fontForAll)
//                }
                for (i in 0 until navMenuItemsCount) {
                    val itemView = vg.getChildAt(i)
                    if (itemView is NavigationMenuItemView) {
                        val checkedTextView = itemView.getChildAt(0) as CheckedTextView?
//                        checkedTextView?.setTypeface(fontFace, Typeface.BOLD)
                        //TODO iplm logic set font here
                    }
                }
            }
        }
    }
}

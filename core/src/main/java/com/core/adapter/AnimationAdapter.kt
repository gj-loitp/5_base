package com.core.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LAnimationUtil

abstract class AnimationAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastPosition = -1

    fun setAnimation(viewToAnimate: View?, position: Int) {
        // If the bound view wasn't previously displayed on screen, it's animated
        viewToAnimate?.let {
            if (position > lastPosition) {
                LAnimationUtil.playAnimRandomDuration(viewToAnimate = it)
                lastPosition = position
            }
        }
    }
}

package com.loitpcore.views.actionBar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.github.mmin18.widget.RealtimeBlurView
import com.loitpcore.R
import com.loitpcore.core.utilities.LAppResource
import com.loitpcore.core.utilities.LUIUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LActionBar : RelativeLayout {
    var ivIconLeft: ImageView? = null
    var ivIconRight: ImageView? = null
    var tvTitle: TextView? = null
    var realtimeBlurView: RealtimeBlurView? = null
    var viewShadow: View? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_l_action_bar, this)

        this.ivIconLeft = findViewById(R.id.ivIconLeft)
        this.ivIconRight = findViewById(R.id.ivIconRight)
        this.tvTitle = findViewById(R.id.tvTitle)
        this.realtimeBlurView = findViewById(R.id.realtimeBlurView)
        this.viewShadow = findViewById(R.id.viewShadow)

        if (!isInEditMode) {
            if (LUIUtil.isDarkTheme()) {
                ivIconLeft?.setColorFilter(Color.WHITE)
                ivIconRight?.setColorFilter(Color.WHITE)
                tvTitle?.setTextColor(Color.WHITE)
                realtimeBlurView?.setOverlayColor(LAppResource.getColor(R.color.colorPrimaryDark))
            } else {
                ivIconLeft?.setColorFilter(Color.BLACK)
                ivIconRight?.setColorFilter(Color.BLACK)
                tvTitle?.setTextColor(Color.BLACK)
                realtimeBlurView?.setOverlayColor(LAppResource.getColor(R.color.colorPrimaryLight))
            }
        }
    }
}

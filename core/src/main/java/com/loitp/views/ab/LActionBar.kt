package com.loitp.views.ab

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.loitp.core.utilities.LUIUtil
import com.loitp.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LActionBar : RelativeLayout {
    var mcv: MaterialCardView? = null
    var ivIconLeft: ImageView? = null
    var ivIconRight: ImageView? = null
    var tvTitle: TextView? = null

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.l_v_action_bar, this)

        this.mcv = findViewById(R.id.mcv)
        this.ivIconLeft = findViewById(R.id.ivIconLeft)
        this.ivIconRight = findViewById(R.id.ivIconRight)
        this.tvTitle = findViewById(R.id.tvTitle)

        if (!isInEditMode) {
            if (LUIUtil.isDarkTheme()) {
                ivIconLeft?.setColorFilter(Color.WHITE)
                ivIconRight?.setColorFilter(Color.WHITE)
                tvTitle?.setTextColor(Color.WHITE)
            } else {
                ivIconLeft?.setColorFilter(Color.BLACK)
                ivIconRight?.setColorFilter(Color.BLACK)
                tvTitle?.setTextColor(Color.BLACK)
            }
        }
    }
}

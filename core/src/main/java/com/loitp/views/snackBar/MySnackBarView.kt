package com.loitp.views.snackBar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.loitp.R

class MySnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val ivIconSnackBar: ImageView
    private val tvMessageSnackBar: TextView

    init {
        View.inflate(context, R.layout.v_my_snack_bar, this)
        clipToPadding = false
        this.ivIconSnackBar = findViewById(R.id.ivIconSnackBar)
        this.tvMessageSnackBar = findViewById(R.id.tvMessageSnackBar)

//        this.ivIconSnackBar.setImageResource(R.drawable.ic_launcher)
//        this.tvMessageSnackBar.text = "tvMessageSnackBar"
    }

    fun updateUI(
        resId: Int = R.drawable.ic_launcher,
        msg: String,
    ) {
        this.ivIconSnackBar.setImageResource(resId)
        this.tvMessageSnackBar.text = msg
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(ivIconSnackBar, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(ivIconSnackBar, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(scaleX, scaleY)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) {
    }
}
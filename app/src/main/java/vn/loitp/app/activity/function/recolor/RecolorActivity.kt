package vn.loitp.app.activity.function.recolor

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.simmorsal.recolor_project.ReColor
import kotlinx.android.synthetic.main.activity_fun_recolor.*
import vn.loitp.app.R

// https://github.com/SIMMORSAL/ReColor

@LogTag("RecolorActivity")
@IsFullScreen(false)
class RecolorActivity : BaseFontActivity() {
    private var imageViewColorSetNumber = 0
    private var textViewColorSetNumber = 0
    private var isRootViewColorChanged = false
    private var isCardColorChanged = false
    private var isStatusBarColorChanged = false
    private var isNavigationBarColorChanged = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fun_recolor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        logAListOfColors()
    }

    private fun logAListOfColors() {
        val colorList = ReColor(this).getColorIntArray("E91E63", "1E88E5", 20)
        for (i in colorList.indices) {
            logD(colorList[i].toString())
        }
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecolorActivity::class.java.simpleName
        }

        // changing space_invaders' color
        imageView.setOnClickListener {
            when (imageViewColorSetNumber) {
                0 -> {
                    ReColor(this).setImageViewColorFilter(imageView, "ffffff", "388E3C", 300)
                    imageViewColorSetNumber = 1
                }
                1 -> {
                    ReColor(this).setImageViewColorFilter(imageView, "388E3C", "00838F", 300)
                    imageViewColorSetNumber = 2
                }
                2 -> {
                    ReColor(this).setImageViewColorFilter(imageView, "00838F", "F4511E", 300)
                    imageViewColorSetNumber = 3
                }
                3 -> {
                    ReColor(this).setImageViewColorFilter(imageView, "F4511E", "FFFFFF", 300)
                    imageViewColorSetNumber = 0
                }
            }
        }

        // changing the color on the text view
        textView.setOnClickListener {
            when (textViewColorSetNumber) {
                0 -> {
                    ReColor(this).setTextViewColor(textView, "ffffff", "81D4FA", 300)
                    textViewColorSetNumber = 1
                }
                1 -> {
                    ReColor(this).setTextViewColor(textView, "81D4FA", "ef9a9a", 300)
                    textViewColorSetNumber = 2
                }
                2 -> {
                    ReColor(this).setTextViewColor(textView, "ef9a9a", "FFAB91", 300)
                    textViewColorSetNumber = 3
                }
                3 -> {
                    ReColor(this).setTextViewColor(textView, "FFAB91", "ffffff", 300)
                    textViewColorSetNumber = 0
                }
            }
        }
        linReColorBackground.setOnClickListener {
            isRootViewColorChanged = if (!isRootViewColorChanged) {
                ReColor(this).setViewBackgroundColor(rootView, "004D40", "#880E4F", 10000)
                !isRootViewColorChanged
            } else {
                ReColor(this).setViewBackgroundColor(rootView, "880E4F", "#004D40", 10000)
                !isRootViewColorChanged
            }
        }
        val reColorCardView = ReColor(this)
        reColorCardView.setOnReColorFinish {
            logD("onReColorFinishCallBack It listens")
        }
        theCardView.setOnClickListener {
            isCardColorChanged = if (!isCardColorChanged) {
                reColorCardView.setCardViewColor(theCardView, "1E88E5", "f44336", 3000)
                !isCardColorChanged
            } else {
                reColorCardView.setCardViewColor(theCardView, "f44336", "1E88E5", 3000)
                !isCardColorChanged
            }
        }

        // changing statusBar color
        linRecolorStatusBar.setOnClickListener {
            isStatusBarColorChanged = if (!isStatusBarColorChanged) {
                // if starting color is null, color will be automatically retrieved from status bar
                // same is true for navigation bar
                ReColor(this).setStatusBarColor(null, "880E4F", 2000)
                !isStatusBarColorChanged
            } else {
                ReColor(this).setStatusBarColor(null, "004D40", 2000)
                !isStatusBarColorChanged
            }
        }

        // changing navigationBar color
        linRecolorNavigationBar.setOnClickListener {
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                ReColor(this).setNavigationBarColor(null, "880E4F", 2000)
                !isNavigationBarColorChanged
            } else {
                ReColor(this).setNavigationBarColor(null, "000000", 2000)
                !isNavigationBarColorChanged
            }
        }

        // changing both statusBar and navigationBar colors
        linRecolorBoth.setOnClickListener {
            isStatusBarColorChanged = if (!isStatusBarColorChanged) {
                ReColor(this).setStatusBarColor(null, "880E4F", 2000)
                !isStatusBarColorChanged
            } else {
                ReColor(this).setStatusBarColor(null, "004D40", 2000)
                !isStatusBarColorChanged
            }
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                ReColor(this).setNavigationBarColor(null, "880E4F", 2000)
                !isNavigationBarColorChanged
            } else {
                ReColor(this).setNavigationBarColor(null, "000000", 2000)
                !isNavigationBarColorChanged
            }
        }

        // pulsing statusBar to an orange color for 2 times
        linPulseStatusBar.setOnClickListener {
            ReColor(this).pulseStatusBar("E64A19", 200, 2)
        }

        // pulsing navigationBar to an orange color for 2 times
        linPulseNavigationBar.setOnClickListener {
            ReColor(this).pulseNavigationBar("e64a19", 200, 2)
        }

        // pulsing both colors' to an orange color for 5 times really fast
        linPulseBoth.setOnClickListener {
            ReColor(this).pulseStatusBar("E64A19", 80, 5)
            ReColor(this).pulseNavigationBar("e64a19", 80, 5)
        }
    }
}

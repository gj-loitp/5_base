package vn.loitp.app.activity.function.recolor

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.simmorsal.recolor_project.ReColor
import kotlinx.android.synthetic.main.activity_fun_recolor.*
import vn.loitp.app.R

//https://github.com/SIMMORSAL/ReColor

@LayoutId(R.layout.activity_fun_recolor)
class RecolorActivity : BaseFontActivity() {
    private var imageViewColorSetNumber = 0
    private var textViewColorSetNumber = 0
    private var isRootViewColorChanged = false
    private var isCardColorChanged = false
    private var isStatusBarColorChanged = false
    private var isNavigationBarColorChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onClicks()
        logAListOfColors()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private fun logAListOfColors() {
        val colorList = ReColor(activity).getColorIntArray("E91E63", "1E88E5", 20)
        for (i in colorList.indices) {
            logD(colorList[i].toString())
        }
    }

    private fun onClicks() {
        // changing space_invaders' color
        imageView.setOnClickListener {
            when (imageViewColorSetNumber) {
                0 -> {
                    ReColor(activity).setImageViewColorFilter(imageView, "ffffff", "388E3C", 300)
                    imageViewColorSetNumber = 1
                }
                1 -> {
                    ReColor(activity).setImageViewColorFilter(imageView, "388E3C", "00838F", 300)
                    imageViewColorSetNumber = 2
                }
                2 -> {
                    ReColor(activity).setImageViewColorFilter(imageView, "00838F", "F4511E", 300)
                    imageViewColorSetNumber = 3
                }
                3 -> {
                    ReColor(activity).setImageViewColorFilter(imageView, "F4511E", "FFFFFF", 300)
                    imageViewColorSetNumber = 0
                }
            }
        }

        //changing the color on the text view
        textView.setOnClickListener {
            when (textViewColorSetNumber) {
                0 -> {
                    ReColor(activity).setTextViewColor(textView, "ffffff", "81D4FA", 300)
                    textViewColorSetNumber = 1
                }
                1 -> {
                    ReColor(activity).setTextViewColor(textView, "81D4FA", "ef9a9a", 300)
                    textViewColorSetNumber = 2
                }
                2 -> {
                    ReColor(activity).setTextViewColor(textView, "ef9a9a", "FFAB91", 300)
                    textViewColorSetNumber = 3
                }
                3 -> {
                    ReColor(activity).setTextViewColor(textView, "FFAB91", "ffffff", 300)
                    textViewColorSetNumber = 0
                }
            }
        }
        linReColorBackground.setOnClickListener {
            isRootViewColorChanged = if (!isRootViewColorChanged) {
                ReColor(activity).setViewBackgroundColor(rootView, "004D40", "#880E4F", 10000)
                !isRootViewColorChanged
            } else {
                ReColor(activity).setViewBackgroundColor(rootView, "880E4F", "#004D40", 10000)
                !isRootViewColorChanged
            }
        }
        val reColorCardView = ReColor(activity)
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
                ReColor(activity).setStatusBarColor(null, "880E4F", 2000)
                !isStatusBarColorChanged
            } else {
                ReColor(activity).setStatusBarColor(null, "004D40", 2000)
                !isStatusBarColorChanged
            }
        }

        // changing navigationBar color
        linRecolorNavigationBar.setOnClickListener {
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                ReColor(activity).setNavigationBarColor(null, "880E4F", 2000)
                !isNavigationBarColorChanged
            } else {
                ReColor(activity).setNavigationBarColor(null, "000000", 2000)
                !isNavigationBarColorChanged
            }
        }

        // changing both statusBar and navigationBar colors
        linRecolorBoth.setOnClickListener {
            isStatusBarColorChanged = if (!isStatusBarColorChanged) {
                ReColor(activity).setStatusBarColor(null, "880E4F", 2000)
                !isStatusBarColorChanged
            } else {
                ReColor(activity).setStatusBarColor(null, "004D40", 2000)
                !isStatusBarColorChanged
            }
            isNavigationBarColorChanged = if (!isNavigationBarColorChanged) {
                ReColor(activity).setNavigationBarColor(null, "880E4F", 2000)
                !isNavigationBarColorChanged
            } else {
                ReColor(activity).setNavigationBarColor(null, "000000", 2000)
                !isNavigationBarColorChanged
            }
        }

        // pulsing statusBar to an orange color for 2 times
        linPulseStatusBar.setOnClickListener {
            ReColor(activity).pulseStatusBar("E64A19", 200, 2)
        }

        // pulsing navigationBar to an orange color for 2 times
        linPulseNavigationBar.setOnClickListener {
            ReColor(activity).pulseNavigationBar("e64a19", 200, 2)
        }

        // pulsing both colors' to an orange color for 5 times really fast
        linPulseBoth.setOnClickListener {
            ReColor(activity).pulseStatusBar("E64A19", 80, 5)
            ReColor(activity).pulseNavigationBar("e64a19", 80, 5)
        }
    }
}

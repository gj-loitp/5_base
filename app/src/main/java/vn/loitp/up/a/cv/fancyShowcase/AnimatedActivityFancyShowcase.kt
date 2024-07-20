package vn.loitp.up.a.cv.fancyShowcase

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.loitp.core.base.BaseActivityFancyShowcase
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.recolorNavigationBar
import com.loitp.core.ext.recolorStatusBar
import kotlinx.android.synthetic.main.l_fancy_showcaseanimated_view.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.FocusShape
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R
import vn.loitp.databinding.AFancyShowcaseAnimatedBinding

class AnimatedActivityFancyShowcase : BaseActivityFancyShowcase() {

    private lateinit var queue: FancyShowCaseQueue
    private lateinit var fancyView: FancyShowCaseView
    private lateinit var fancyView2: FancyShowCaseView
    private lateinit var binding: AFancyShowcaseAnimatedBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFancyShowcaseAnimatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fancyView = FancyShowCaseView.Builder(this)
            .focusOn(binding.btnFocus)
            .backgroundColor(Color.YELLOW)
            .focusShape(FocusShape.CIRCLE)
//            .roundRectRadius(90)
            .focusBorderColor(Color.GREEN)
            .focusBorderSize(15)
//            .focusDashedBorder(15.0f, 15.0f)
//            .showOnce("1")
            .dismissListener(object : DismissListener {
                override fun onDismiss(id: String?) {

                }

                override fun onSkipped(id: String?) {

                }
            })
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    this@AnimatedActivityFancyShowcase.recolorStatusBar(
                        startColor = null,
                        endColor = Color.RED
                    )
                    this@AnimatedActivityFancyShowcase.recolorNavigationBar(
                        startColor = null,
                        endColor = Color.RED
                    )
                    setAnimatedContent(fancyView)
                }
            })
            .build()

        fancyView2 = FancyShowCaseView.Builder(this)
            .focusOn(binding.btnFocus2)
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    setAnimatedContent(fancyView2)
                }
            })
            .build()

        binding.btnFocus.setOnClickListener {
            queue = FancyShowCaseQueue().apply {
                add(fancyView)
                add(fancyView2)
                show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAnimatedContent(
        fancyShowCaseView: FancyShowCaseView
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (fancyShowCaseView == fancyView2) {
                tvMain.text = "My Fancy Title 2"
                tvSub.text = "My fancy description can be a longer text.2"
                btnNext.text = "Close"
            }

            btnNext.setOnClickListener {
                fancyShowCaseView.hide()
            }
            btnDismiss.setOnClickListener {
                queue.cancel(true)
            }

            val mainAnimation = AnimationUtils.loadAnimation(
                /* context = */ this@AnimatedActivityFancyShowcase,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            mainAnimation.fillAfter = true

            val subAnimation = AnimationUtils.loadAnimation(
                /* context = */ this@AnimatedActivityFancyShowcase,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            subAnimation.fillAfter = true
            tvMain.startAnimation(mainAnimation)
            Handler(Looper.getMainLooper()).postDelayed({
                tvSub.startAnimation(subAnimation)
            }, 80)
        }, 200)
    }
}

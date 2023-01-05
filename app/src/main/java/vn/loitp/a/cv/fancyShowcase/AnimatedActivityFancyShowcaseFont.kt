package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import kotlinx.android.synthetic.main.a_fancy_showcase.*
import kotlinx.android.synthetic.main.l_fancy_showcaseanimated_view.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R

class AnimatedActivityFancyShowcaseFont : BaseActivityFancyShowcaseFont() {

    private lateinit var queue: FancyShowCaseQueue
    private lateinit var fancyView: FancyShowCaseView
    private lateinit var fancyView2: FancyShowCaseView

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_animated
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fancyView = FancyShowCaseView.Builder(this)
            .focusOn(btnFocus)
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    setAnimatedContent(view, fancyView)
                }
            })
            .build()

        fancyView2 = FancyShowCaseView.Builder(this)
            .focusOn(btnFocus2)
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    setAnimatedContent(view, fancyView2)
                }
            })
            .build()

        btnFocus.setOnClickListener {
            queue = FancyShowCaseQueue().apply {
                add(fancyView)
                add(fancyView2)
                show()
            }
        }
    }

    private fun setAnimatedContent(
        view: View,
        fancyShowCaseView: FancyShowCaseView
    ) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (fancyShowCaseView == fancyView2) {
                tvMain.text = "My Fancy Title 2"
                tvSub.text = "My fancy description can be a longer text.2"
                btn_next.text = "Close"
            }

            btn_next.setOnClickListener { fancyShowCaseView.hide() }
            btn_dismiss.setOnClickListener { queue.cancel(true) }

            val mainAnimation = AnimationUtils.loadAnimation(
                /* context = */ this@AnimatedActivityFancyShowcaseFont,
                /* id = */ R.anim.slide_in_left_fancy_showcase
            )
            mainAnimation.fillAfter = true

            val subAnimation = AnimationUtils.loadAnimation(
                /* context = */ this@AnimatedActivityFancyShowcaseFont,
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

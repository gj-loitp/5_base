package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.loitp.core.base.BaseActivityFancyShowcase
import kotlinx.android.synthetic.main.a_fancy_showcase.*

import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R

class AnimatedActivityFancyShowcase : BaseActivityFancyShowcase() {

    private lateinit var queue: FancyShowCaseQueue
    private lateinit var fancyView: FancyShowCaseView
    private lateinit var fancyView2: FancyShowCaseView

    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_animated
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fancyView = FancyShowCaseView.Builder(this)
            .focusOn(btn_focus)
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    setAnimatedContent(view, fancyView)
                }
            })
            .build()

        fancyView2 = FancyShowCaseView.Builder(this)
            .focusOn(btn_focus2)
            .customView(R.layout.l_fancy_showcaseanimated_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    setAnimatedContent(view, fancyView2)
                }
            })
            .build()

        btn_focus.setOnClickListener {
            queue = FancyShowCaseQueue().apply {
                add(fancyView)
                add(fancyView2)
                show()
            }
        }


    }

    private fun setAnimatedContent(view: View, fancyShowCaseView: FancyShowCaseView) {
        Handler().postDelayed({
            val tvMain = view.findViewById<View>(R.id.tvMain) as TextView
            val tvSub = view.findViewById<View>(R.id.tvSub) as TextView
            val tvNext = view.findViewById<View>(R.id.btn_next) as TextView
            val tvDismiss = view.findViewById<View>(R.id.btn_dismiss) as TextView

            if (fancyShowCaseView == fancyView2) {
                tvMain.text = "My Fancy Title 2"
                tvSub.text = "My fancy description can be a longer text.2"
                tvNext.text = "Close"
            }

            tvNext.setOnClickListener { fancyShowCaseView.hide() }

            tvDismiss.setOnClickListener { queue.cancel(true) }

            val mainAnimation =
                AnimationUtils.loadAnimation(
                    this@AnimatedActivityFancyShowcase,
                    R.anim.slide_in_left_fancy_showcase
                )
            mainAnimation.fillAfter = true

            val subAnimation =
                AnimationUtils.loadAnimation(
                    this@AnimatedActivityFancyShowcase,
                    R.anim.slide_in_left_fancy_showcase
                )
            subAnimation.fillAfter = true
            tvMain.startAnimation(mainAnimation)
            Handler().postDelayed({ tvSub.startAnimation(subAnimation) }, 80)
        }, 200)
    }
}

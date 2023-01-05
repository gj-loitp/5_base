package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import kotlinx.android.synthetic.main.a_fancy_showcase_queue.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R

abstract class CustomQueueActivityFontFancyShowcase : BaseActivityFancyShowcaseFont() {

    private lateinit var queue: FancyShowCaseQueue

    private var mClickListener: View.OnClickListener = View.OnClickListener {
        queue.current?.hide()
    }

    private var dismissListener = object : DismissListener {
        override fun onDismiss(id: String?) {
        }

        override fun onSkipped(id: String?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_fancy_showcase_queue)

        val fancyShowCaseView1 = FancyShowCaseView.Builder(this)
            .title("First Queue Item")
            .focusOn(btnQueue1)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btnAction1).setOnClickListener(mClickListener)
                }
            })
            .closeOnTouch(false)
            .dismissListener(dismissListener)
            .build()

        val fancyShowCaseView2 = FancyShowCaseView.Builder(this)
            .title("Second Queue Item")
            .focusOn(btnQueue2)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btnAction1).setOnClickListener(mClickListener)
                }
            })
            .closeOnTouch(false)
            .dismissListener(dismissListener)
            .build()

        val fancyShowCaseView3 = FancyShowCaseView.Builder(this)
            .title("Third Queue Item")
            .focusOn(btnQueue3!!)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btnAction1).setOnClickListener(mClickListener)
                }
            })
            .closeOnTouch(false)
            .dismissListener(dismissListener)
            .build()

        queue = FancyShowCaseQueue()
            .add(fancyShowCaseView1)
            .add(fancyShowCaseView2)
            .add(fancyShowCaseView3)

        queue.show()
    }

}

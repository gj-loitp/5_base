package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.a_fancy_showcase_queue.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.DismissListener
import me.toptas.fancyshowcase.listener.OnViewInflateListener
import vn.loitp.R

abstract class CustomQueueActivityFancyShowcase : BaseActivityFancyShowcase() {

    private lateinit var queue: FancyShowCaseQueue

    private var mClickListener: View.OnClickListener = View.OnClickListener {
        Log.d("", "onClick: ")
        queue.current?.hide()
    }

    private var dismissListener = object : DismissListener {
        override fun onDismiss(id: String?) {
            Log.v("asd", "dismiss")
        }

        override fun onSkipped(id: String?) {
            Log.v("asd", "skipped")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_fancy_showcase_queue)

        val fancyShowCaseView1 = FancyShowCaseView.Builder(this)
            .title("First Queue Item")
            .focusOn(btn_queue_1)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btn_action_1).setOnClickListener(mClickListener)
                }
            })
            .closeOnTouch(false)
            .dismissListener(dismissListener)
            .build()

        val fancyShowCaseView2 = FancyShowCaseView.Builder(this)
            .title("Second Queue Item")
            .focusOn(btn_queue_2)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btn_action_1).setOnClickListener(mClickListener)
                }
            })
            .closeOnTouch(false)
            .dismissListener(dismissListener)
            .build()

        val fancyShowCaseView3 = FancyShowCaseView.Builder(this)
            .title("Third Queue Item")
            .focusOn(btn_queue_3!!)
            .customView(R.layout.l_fancy_showcase_my_custom_view, object : OnViewInflateListener {
                override fun onViewInflated(view: View) {
                    view.findViewById<View>(R.id.btn_action_1).setOnClickListener(mClickListener)
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

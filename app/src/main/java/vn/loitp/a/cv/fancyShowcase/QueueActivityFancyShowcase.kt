package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.widget.Toast
import com.loitp.core.base.BaseActivityFancyShowcase
import kotlinx.android.synthetic.main.a_fancy_showcase_queue.*
import me.toptas.fancyshowcase.FancyShowCaseQueue
import me.toptas.fancyshowcase.FancyShowCaseView
import me.toptas.fancyshowcase.listener.OnCompleteListener
import vn.loitp.R


abstract class QueueActivityFancyShowcase : BaseActivityFancyShowcase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_fancy_showcase_queue)

        val fancyShowCaseView1 = FancyShowCaseView.Builder(this)
            .title("First Queue Item")
            .focusOn(btn_queue_1)
            .build()

        val fancyShowCaseView2 = FancyShowCaseView.Builder(this)
            .title("Second Queue Item")
            .focusOn(btn_queue_2)
            .build()

        val fancyShowCaseView3 = FancyShowCaseView.Builder(this)
            .title("Third Queue Item")
            .focusOn(btn_queue_3)
            .build()

        val queue = FancyShowCaseQueue()
            .add(fancyShowCaseView1)
            .add(fancyShowCaseView2)
            .add(fancyShowCaseView3)
        queue.show()
        queue.completeListener = object : OnCompleteListener {
            override fun onComplete() {
                Toast.makeText(this@QueueActivityFancyShowcase, "Finished", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}

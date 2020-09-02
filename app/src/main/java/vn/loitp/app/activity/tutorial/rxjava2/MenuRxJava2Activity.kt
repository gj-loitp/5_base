package vn.loitp.app.activity.tutorial.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil.Companion.tranIn
import kotlinx.android.synthetic.main.activity_menu_rx_java2.*
import vn.loitp.app.R

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
class MenuRxJava2Activity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btDisposableExampleActivity.setOnClickListener(this)
        btFlowableExampleActivity.setOnClickListener(this)
        btIntervalExampleActivity.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt5.setOnClickListener(this)
        btAsyncTaskAndRx.setOnClickListener(this)
        btTestRx.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_rx_java2
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDisposableExampleActivity -> intent = Intent(activity, DisposableExampleActivity::class.java)
            btFlowableExampleActivity -> intent = Intent(activity, FlowableExampleActivity::class.java)
            btIntervalExampleActivity -> intent = Intent(activity, IntervalExampleActivity::class.java)
            bt3 -> intent = Intent(activity, SingleObserverExampleActivity::class.java)
            bt4 -> intent = Intent(activity, CompletableObserverExampleActivity::class.java)
            bt5 -> intent = Intent(activity, MapExampleActivity::class.java)
            btAsyncTaskAndRx -> intent = Intent(activity, AsyncTaskRxActivity::class.java)
            btTestRx -> intent = Intent(activity, TestRxActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            tranIn(activity)
        }
    }
}

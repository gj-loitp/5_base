package vn.loitp.app.activity.tutorial.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil.Companion.tranIn
import kotlinx.android.synthetic.main.activity_menu_rx_java2.*
import vn.loitp.app.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("MenuRxJava2Activity")
@IsFullScreen(false)
class MenuRxJava2Activity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_rx_java2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btDisposableExampleActivity.setOnClickListener(this)
        btFlowableExampleActivity.setOnClickListener(this)
        btIntervalExampleActivity.setOnClickListener(this)
        btSingleObserverExampleActivity.setOnClickListener(this)
        bt4.setOnClickListener(this)
        btMapExampleActivity.setOnClickListener(this)
        btAsyncTaskAndRx.setOnClickListener(this)
        btTestRx.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btDisposableExampleActivity ->
                intent =
                    Intent(this, DisposableExampleActivity::class.java)
            btFlowableExampleActivity -> intent = Intent(this, FlowAbleExampleActivity::class.java)
            btIntervalExampleActivity -> intent = Intent(this, IntervalExampleActivity::class.java)
            btSingleObserverExampleActivity ->
                intent =
                    Intent(this, SingleObserverExampleActivity::class.java)
            bt4 -> intent = Intent(this, CompletableObserverExampleActivity::class.java)
            btMapExampleActivity -> intent = Intent(this, MapExampleActivity::class.java)
            btAsyncTaskAndRx -> intent = Intent(this, AsyncTaskRxActivity::class.java)
            btTestRx -> intent = Intent(this, TestRxActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            tranIn(this)
        }
    }
}

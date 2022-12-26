package vn.loitp.a.tut.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil.Companion.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_rx_java2_menu.*
import vn.loitp.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("MenuRxJava2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRxJava2Activity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rx_java2_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
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
            this.tvTitle?.text = MenuRxJava2Activity::class.java.simpleName
        }
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

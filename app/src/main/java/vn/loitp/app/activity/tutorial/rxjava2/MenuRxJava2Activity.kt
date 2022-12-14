package vn.loitp.app.activity.tutorial.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil.Companion.tranIn
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_rx_java2.*
import vn.loitp.app.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("MenuRxJava2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRxJava2Activity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_rx_java2
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

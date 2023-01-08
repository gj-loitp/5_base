package vn.loitp.a.tut.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_rx_java2_menu.*
import vn.loitp.R

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("MenuRxJava2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRxJava2ActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rx_java2_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuRxJava2ActivityFont::class.java.simpleName
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
                    Intent(this, DisposableExampleActivityFont::class.java)
            btFlowableExampleActivity -> intent = Intent(this, FlowAbleExampleActivityFont::class.java)
            btIntervalExampleActivity -> intent = Intent(this, IntervalExampleActivityFont::class.java)
            btSingleObserverExampleActivity ->
                intent =
                    Intent(this, SingleObserverExampleActivityFont::class.java)
            bt4 -> intent = Intent(this, CompletableObserverExampleActivityFont::class.java)
            btMapExampleActivity -> intent = Intent(this, MapExampleActivityFont::class.java)
            btAsyncTaskAndRx -> intent = Intent(this, AsyncTaskRxActivityFont::class.java)
            btTestRx -> intent = Intent(this, TestRxActivityFont::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}

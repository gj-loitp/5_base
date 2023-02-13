package vn.loitp.up.a.tut.rxjava2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import vn.loitp.R
import vn.loitp.databinding.ARxJava2MenuBinding

// https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

@LogTag("MenuRxJava2Activity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuRxJava2Activity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: ARxJava2MenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARxJava2MenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuRxJava2Activity::class.java.simpleName
        }
        binding.btDisposableExampleActivity.setOnClickListener(this)
        binding.btFlowableExampleActivity.setOnClickListener(this)
        binding.btIntervalExampleActivity.setOnClickListener(this)
        binding.btSingleObserverExampleActivity.setOnClickListener(this)
        binding.bt4.setOnClickListener(this)
        binding.btMapExampleActivity.setOnClickListener(this)
        binding.btAsyncTaskAndRx.setOnClickListener(this)
        binding.btTestRx.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            binding.btDisposableExampleActivity -> intent =
                Intent(this, DisposableExampleActivity::class.java)
            binding.btFlowableExampleActivity -> intent =
                Intent(this, FlowAbleExampleActivity::class.java)
            binding.btIntervalExampleActivity -> intent =
                Intent(this, IntervalExampleActivity::class.java)
            binding.btSingleObserverExampleActivity -> intent =
                Intent(this, SingleObserverExampleActivity::class.java)
            binding.bt4 -> intent = Intent(this, CompletableObserverExampleActivity::class.java)
            binding.btMapExampleActivity -> intent = Intent(this, MapExampleActivity::class.java)
            binding.btAsyncTaskAndRx -> intent = Intent(this, AsyncTaskRxActivity::class.java)
            binding.btTestRx -> intent = Intent(this, TestRxActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}

package vn.loitp.up.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.func.activityAndService.ActivityServiceCommunicateActivityFont
import vn.loitp.databinding.A0Binding

@LogTag("EmptyActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class EmptyActivity : BaseActivityFont() {

    private lateinit var binding: A0Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = A0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        testInputData()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/tplloi/base"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testInputData() {
        val input = intent.getStringExtra(ActivityServiceCommunicateActivityFont.KEY_INPUT)
        if (input.isNullOrEmpty()) {
            //do nothing
        } else {
            binding.tv.text = "Click me to back screen $input"
            binding.tv.setSafeOnClickListener {
                setResultActivity { data ->
                    data.putExtra(
                        /* name = */ ActivityServiceCommunicateActivityFont.KEY_OUTPUT,
                        /* value = */ "You are the best!!! ${System.currentTimeMillis()}"
                    )
                }
            }
        }
    }
}

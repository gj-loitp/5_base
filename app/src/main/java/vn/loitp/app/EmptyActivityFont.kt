package vn.loitp.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_0.*
import vn.loitp.R
import vn.loitp.a.func.activityAndService.ActivityServiceCommunicateActivityFont

@LogTag("EmptyActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class EmptyActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        testInputData()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/tplloi/base"
                        )
                    }
                )
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivityFont::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun testInputData() {
        val input = intent.getStringExtra(ActivityServiceCommunicateActivityFont.KEY_INPUT)
        if (input.isNullOrEmpty()) {
            //do nothing
        } else {
            tv.text = "Click me to back screen $input"
            tv.setSafeOnClickListener {
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

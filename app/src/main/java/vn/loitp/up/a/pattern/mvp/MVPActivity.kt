package vn.loitp.up.a.pattern.mvp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.APatternMvpBinding

@LogTag("MVPActivity")
@IsFullScreen(false)
class MVPActivity : BaseActivityFont(), DemoPresenter.View {
    private lateinit var binding: APatternMvpBinding
    private lateinit var demoPresenter: DemoPresenter

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APatternMvpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MVPActivity::class.java.simpleName
        }

        demoPresenter = DemoPresenter(this)

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateFullName(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        binding.btLongTask.setSafeOnClickListener {
            demoPresenter.doALongTask()
        }
    }

    override fun updateUserInfoTextView(info: String) {
        binding.textView.text = info
    }

    override fun showProgressBar() {
        binding.pb.isVisible = true
    }

    override fun hideProgressBar() {
        binding.pb.isVisible = false
    }

    override fun onDoALongTask(result: String) {
        binding.tvDoAlongTask.text = result
    }

    override fun onDestroy() {
        demoPresenter.view = null
        super.onDestroy()
    }
}

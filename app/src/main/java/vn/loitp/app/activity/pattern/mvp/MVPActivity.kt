package vn.loitp.app.activity.pattern.mvp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_pattern_mvp.*
import vn.loitp.app.R

@LogTag("MVPActivity")
@IsFullScreen(false)
class MVPActivity : BaseFontActivity(), DemoPresenter.View {

    private lateinit var demoPresenter: DemoPresenter

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pattern_mvp
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MVPActivity::class.java.simpleName
        }

        demoPresenter = DemoPresenter(this)

        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateFullName(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        btLongTask.setSafeOnClickListener {
            demoPresenter.doALongTask()
        }
    }

    override fun updateUserInfoTextView(info: String) {
        textView.text = info
    }

    override fun showProgressBar() {
        pb.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        pb.visibility = View.INVISIBLE
    }

    override fun onDoALongTask(result: String) {
        tvDoAlongTask.text = result
    }

    override fun onDestroy() {
        demoPresenter.view = null
        super.onDestroy()
    }
}

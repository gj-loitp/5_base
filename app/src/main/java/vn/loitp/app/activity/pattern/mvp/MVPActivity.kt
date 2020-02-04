package vn.loitp.app.activity.pattern.mvp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_mvp.*
import vn.loitp.app.R

class MVPActivity : BaseFontActivity(), DemoPresenter.View {

    private lateinit var demoPresenter: DemoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_mvp
    }

    override fun updateUserInfoTextView(info: String) {
        myTextView.text = info
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

package vn.loitp.app.activity.pattern.mvp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.core.base.BaseFontActivity
import loitp.basemaster.R


class MVPActivity : BaseFontActivity(), DemoPresenter.View {
    private lateinit var demoPresenter: DemoPresenter
    private lateinit var myTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        demoPresenter = DemoPresenter(this)

        progressBar = findViewById(R.id.pb)
        myTextView = findViewById(R.id.myTextView)

        val userNameEt = findViewById<EditText>(R.id.username)
        val emailEt = findViewById<EditText>(R.id.email)

        userNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateFullName(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                demoPresenter.updateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })
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
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}

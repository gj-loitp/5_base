package vn.loitp.app.activity.customviews.textview.autofittextview

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class AutoFitTextViewActivity : BaseFontActivity() {
    private var mOutput: TextView? = null
    private var mAutofitOutput: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mOutput = findViewById(R.id.output)
        mAutofitOutput = findViewById(R.id.output_autofit)

        (findViewById<EditText>(R.id.input)).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                // do nothing
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
                mOutput!!.text = charSequence
                mAutofitOutput!!.text = charSequence
            }

            override fun afterTextChanged(editable: Editable) {
                // do nothing
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_autofit_textview
    }

}

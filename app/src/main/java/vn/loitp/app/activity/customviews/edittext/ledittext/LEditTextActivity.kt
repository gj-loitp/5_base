package vn.loitp.app.activity.customviews.edittext.ledittext

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.edittext.leditext.LEditText
import kotlinx.android.synthetic.main.activity_l_edit_text.*
import loitp.basemaster.R

class LEditTextActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lEditTextId.apply {
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            colorFocus = ContextCompat.getColor(activity, R.color.colorPrimary)
            colorUnfocus = ContextCompat.getColor(activity, R.color.Black)
            ivLeft.setImageResource(R.mipmap.ic_launcher)
            ivRight.setImageResource(R.drawable.remove)
            setMaxLines(1)
            setInputType(InputType.TYPE_CLASS_TEXT)
            setImeiActionEditText(EditorInfo.IME_ACTION_NEXT, Runnable {
                lEditTextPw.editText.requestFocus()
            })
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    setText("")
                }

                override fun onTextChanged(s: String) {
                    LLog.d(TAG, "onTextChanged s: $s")
                    if (s.isEmpty()) {
                        ivRight.visibility = View.GONE
                    } else {
                        ivRight.visibility = View.VISIBLE
                    }
                    hideMessage()
                }

                override fun setOnFocusChangeListener(isFocus: Boolean) {
                    LLog.d(TAG, "setOnFocusChangeListener isFocus: $isFocus")
                }

            }
        }

        btLogin.setOnClickListener {
            val id = lEditTextId.editText.text.toString()
            val pw = lEditTextPw.editText.text.toString()
            if (id == "loitp") {
                if (pw == "123456789") {
                    showShort("Correct!!!")
                } else {
                    lEditTextPw.showMessage("Wrong pw!!!")
                }
            } else {
                lEditTextId.showMessage("Wrong id!!!")
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_edit_text
    }
}

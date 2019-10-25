package vn.loitp.app.activity.customviews.edittext.ledittext

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LKeyBoardUtil
import com.core.utilities.LLog
import com.core.utilities.LScreenUtil
import com.views.edittext.leditext.LEditText
import kotlinx.android.synthetic.main.activity_l_edit_text.*
import loitp.basemaster.R

class LEditTextActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lEditTextId.apply {
            colorFocus = ContextCompat.getColor(activity, R.color.Black)
            colorUnfocus = ContextCompat.getColor(activity, R.color.Blue)
            colorError = ContextCompat.getColor(activity, R.color.Red)
            ivLeft.setImageResource(R.mipmap.ic_launcher)
            ivRight.setImageResource(R.drawable.remove)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Account"
            setMaxLines(1)
            setWidthRootView(LScreenUtil.screenWidth * 3 / 4)
            setHeightRootView(350)
            //disableEditing()
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
        var isShowPw = false
        lEditTextPw.apply {
            colorFocus = ContextCompat.getColor(activity, R.color.Black)
            colorUnfocus = ContextCompat.getColor(activity, R.color.Blue)
            colorError = ContextCompat.getColor(activity, R.color.Red)
            ivLeft.setImageResource(R.mipmap.ic_launcher)
            ivRight.setImageResource(R.drawable.l_baseline_visibility_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Password"
            setMaxLines(1)
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setImeiActionEditText(EditorInfo.IME_ACTION_DONE, Runnable {
                LKeyBoardUtil.hide(activity)
            })
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    if (isShowPw) {
                        ivRight.setImageResource(R.drawable.l_baseline_visibility_black_48dp)
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = false
                    } else {
                        ivRight.setImageResource(R.drawable.l_baseline_visibility_off_black_48dp)
                        editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = true
                    }
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
            var isCorrectId = false
            var isCorrectPw = false

            if (id == "loitp") {
                isCorrectId = true
            } else {
                lEditTextId.showMessage("Wrong id!!!")
            }
            if (pw == "123456789") {
                isCorrectPw = true
            } else {
                lEditTextPw.showMessage("Wrong pw!!!")
            }
            if (isCorrectId && isCorrectPw) {
                showShort("Correct!!!")
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

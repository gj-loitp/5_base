package vn.loitp.app.activity.customviews.edittext.lEdittext

import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LKeyBoardUtil
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import com.loitp.views.et.l.LEditText
import kotlinx.android.synthetic.main.activity_l_edittext.*
import vn.loitp.app.R

@LogTag("LEditTextActivity")
@IsFullScreen(false)
class LEditTextActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_edittext
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
            this.tvTitle?.text = LEditTextActivity::class.java.simpleName
        }
        lEditTextId.apply {
            colorFocus = LAppResource.getColor(R.color.black)
            colorUnFocus = LAppResource.getColor(R.color.blue)
            colorError = LAppResource.getColor(R.color.red)
            ivLeft.setImageResource(R.drawable.ic_launcher_loitp)
            ivRight.setImageResource(R.drawable.ic_close_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Account"
            setMaxLines(1)
            setWidthRootView(LScreenUtil.screenWidth * 3 / 4)
            setHeightRootView(350)
            // disableEditing()
            setInputType(InputType.TYPE_CLASS_TEXT)
            setImeiActionEditText(
                imeOptions = EditorInfo.IME_ACTION_NEXT,
                runnable = {
                    lEditTextPw.editText.requestFocus()
                }
            )
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    setText("")
                }

                override fun onTextChanged(s: String) {
                    if (s.isEmpty()) {
                        ivRight.visibility = View.GONE
                    } else {
                        ivRight.visibility = View.VISIBLE
                    }
                    hideMessage()
                }

                override fun setOnFocusChangeListener(isFocus: Boolean) {
                    logD("setOnFocusChangeListener isFocus: $isFocus")
                }
            }
        }
        var isShowPw = false
        lEditTextPw.apply {
            colorFocus = LAppResource.getColor(R.color.black)
            colorUnFocus = LAppResource.getColor(R.color.blue)
            colorError = LAppResource.getColor(R.color.red)
            ivLeft.setImageResource(R.drawable.ic_launcher_loitp)
            ivRight.setImageResource(R.drawable.ic_visibility_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Password"
            setMaxLines(1)
            setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            setImeiActionEditText(
                imeOptions = EditorInfo.IME_ACTION_DONE,
                runnable = {
                    LKeyBoardUtil.hide(this@LEditTextActivity)
                }
            )
            callback = object : LEditText.Callback {
                override fun onClickIvRight(imageView: ImageView) {
                    if (isShowPw) {
                        ivRight.setImageResource(R.drawable.ic_visibility_black_48dp)
                        editText.transformationMethod = PasswordTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = false
                    } else {
                        ivRight.setImageResource(R.drawable.ic_visibility_off_black_48dp)
                        editText.transformationMethod =
                            HideReturnsTransformationMethod.getInstance()
                        setLastCursorEditText()
                        isShowPw = true
                    }
                }

                override fun onTextChanged(s: String) {
                    if (s.isEmpty()) {
                        ivRight.visibility = View.GONE
                    } else {
                        ivRight.visibility = View.VISIBLE
                    }
                    hideMessage()
                }

                override fun setOnFocusChangeListener(isFocus: Boolean) {
                    logD("setOnFocusChangeListener isFocus: $isFocus")
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
                showShortInformation("Correct!!!")
            }
        }
    }
}

package vn.loitp.up.a.cv.et.l

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
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideKeyboard
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.et.l.LEditText
import vn.loitp.R
import vn.loitp.databinding.AEtLBinding

@LogTag("LEditTextActivity")
@IsFullScreen(false)
class LEditTextActivity : BaseActivityFont() {
    private lateinit var binding: AEtLBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtLBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = LEditTextActivity::class.java.simpleName
        }
        binding.lEditTextId.apply {
            colorFocus = getColor(R.color.black)
            colorUnFocus = getColor(R.color.blue)
            colorError = getColor(R.color.red)
            ivLeft.setImageResource(R.drawable.ic_launcher_loitp)
            ivRight.setImageResource(R.drawable.ic_close_black_48dp)
            setStrokeWidth(5)
            setCardElevation(15f)
            setCardBackgroundColor(Color.WHITE)
            setCardRadius(45f)
            setPaddingDp(5f)
            editText.hint = "Account"
            setMaxLines(1)
            setWidthRootView(screenWidth * 3 / 4)
            setHeightRootView(350)
            // disableEditing()
            setInputType(InputType.TYPE_CLASS_TEXT)
            setImeiActionEditText(
                imeOptions = EditorInfo.IME_ACTION_NEXT,
                runnable = {
                    binding.lEditTextPw.editText.requestFocus()
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
        binding.lEditTextPw.apply {
            colorFocus = getColor(R.color.black)
            colorUnFocus = getColor(R.color.blue)
            colorError = getColor(R.color.red)
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
                    this@LEditTextActivity.hideKeyboard()
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

        binding.btLogin.setOnClickListener {
            val id = binding.lEditTextId.editText.text.toString()
            val pw = binding.lEditTextPw.editText.text.toString()
            var isCorrectId = false
            var isCorrectPw = false

            if (id == "loitp") {
                isCorrectId = true
            } else {
                binding.lEditTextId.showMessage("Wrong id!!!")
            }
            if (pw == "123456789") {
                isCorrectPw = true
            } else {
                binding.lEditTextPw.showMessage("Wrong pw!!!")
            }
            if (isCorrectId && isCorrectPw) {
                showShortInformation("Correct!!!")
            }
        }
    }
}

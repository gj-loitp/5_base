package vn.loitp.app.activity.customviews.edittext.ledittext

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.edittext.leditext.LEditText
import kotlinx.android.synthetic.main.activity_l_edit_text.*
import loitp.basemaster.R

class LEditTextActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lEditText.setStrokeWidth(5)
        lEditText.setCardElevation(15f)
        lEditText.setCardBackgroundColor(Color.WHITE)
        lEditText.setCardRadius(45f)
        lEditText.colorFocus = ContextCompat.getColor(activity, R.color.colorPrimary)
        lEditText.colorUnfocus = ContextCompat.getColor(activity, R.color.Black)
        lEditText.ivLeft.setImageResource(R.mipmap.ic_launcher)
        lEditText.showMessage("Message!!!")
        lEditText.callback = object : LEditText.Callback {
            override fun setOnFocusChangeListener(isFocus: Boolean) {
                LLog.d(TAG, "setOnFocusChangeListener isFocus $isFocus")
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

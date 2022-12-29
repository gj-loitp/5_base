package vn.loitp.a.cv.sw.toggleButtonGroup.bt

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import com.nex3z.togglebuttongroup.button.OnCheckedChangeListener
import com.nex3z.togglebuttongroup.button.ToggleButton

class CustomToggleButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(context, attrs), ToggleButton {

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }

    private var mChecked = false
    private var mOnCheckedChangeListener: OnCheckedChangeListener? = null
    private var mBroadcasting = false

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            refreshDrawableState()
            if (mBroadcasting) {
                return
            }
            mBroadcasting = true
            mOnCheckedChangeListener?.onCheckedChanged(this, mChecked)
            mBroadcasting = false
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener) {
        mOnCheckedChangeListener = listener
    }
}

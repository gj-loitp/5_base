package vn.loitp.app.activity.customviews.button.autosizebutton

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil.toggleScreenOritation
import com.core.utilities.LScreenUtil.screenWidth
import kotlinx.android.synthetic.main.activity_button_autosize.*
import vn.loitp.app.R

class AutoSizeButtonActivity : BaseFontActivity(), View.OnClickListener {
    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_autosize
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btRotate.setOnClickListener(this)

        bt0.setPortraitSizeWInDp(50f)
        bt0.setPortraitSizeHInDp(50f)
        bt0.setLandscapeSizeWInDp(250f)
        bt0.setLandscapeSizeHInDp(250f)
        bt0.setOnClickListener(this)

        bt1.setPortraitSizeWInDp(150f)
        bt1.setPortraitSizeHInDp(150f)
        bt1.setLandscapeSizeWInDp(100f)
        bt1.setLandscapeSizeHInDp(100f)
        bt1.setOnClickListener(this)

        bt2.setPortraitSizeWInPx(screenWidth)
        bt2.setPortraitSizeHInPx(screenWidth / 10)
        bt2.setLandscapeSizeWInPx(screenWidth / 2)
        bt2.setLandscapeSizeHInPx(screenWidth / 2)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btRotate -> toggleScreenOritation(activity)
            bt0, bt1, bt2 -> showShort("Click")
        }
    }
}

package vn.loitp.a.cv.bt.autoSize

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.toggleScreenOrientation
import kotlinx.android.synthetic.main.a_auto_size_button.*
import vn.loitp.R

@LogTag("AutoSizeButtonActivity")
@IsFullScreen(false)
class AutoSizeButtonActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_auto_size_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
            btRotate -> toggleScreenOrientation()
            bt0, bt1, bt2 -> showShortInformation("Click")
        }
    }
}

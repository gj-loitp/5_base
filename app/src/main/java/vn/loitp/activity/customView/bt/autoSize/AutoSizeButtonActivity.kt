package vn.loitp.activity.customView.bt.autoSize

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.activity_auto_size_button.*
import vn.loitp.R

@LogTag("AutoSizeButtonActivity")
@IsFullScreen(false)
class AutoSizeButtonActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auto_size_button
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

        bt2.setPortraitSizeWInPx(LScreenUtil.screenWidth)
        bt2.setPortraitSizeHInPx(LScreenUtil.screenWidth / 10)
        bt2.setLandscapeSizeWInPx(LScreenUtil.screenWidth / 2)
        bt2.setLandscapeSizeHInPx(LScreenUtil.screenWidth / 2)
        bt2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btRotate -> LActivityUtil.toggleScreenOrientation(this)
            bt0, bt1, bt2 -> showShortInformation("Click")
        }
    }
}

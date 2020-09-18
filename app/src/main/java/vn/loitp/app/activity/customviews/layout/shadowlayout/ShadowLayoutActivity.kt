package vn.loitp.app.activity.customviews.layout.shadowlayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.utils.util.ConvertUtils
import kotlinx.android.synthetic.main.activity_layout_shadow.*
import vn.loitp.app.R

//https://github.com/lijiankun24/ShadowLayout

@LayoutId(R.layout.activity_layout_shadow)
@LogTag("ShadowLayoutActivity")
class ShadowLayoutActivity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvChangeOval.setOnClickListener(this)
        tvChangeRadius.setOnClickListener(this)
        tvChangeRectangle.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun onClick(v: View) {
        when (v) {
            tvChangeOval -> slOval.setShadowColor(ContextCompat.getColor(this, R.color.black50))
            tvChangeRadius -> slRectangle.setShadowColor(Color.parseColor("#EE00FF7F"))
            tvChangeRectangle -> slRadius.setShadowRadius(ConvertUtils.dp2px(12f).toFloat())
        }
    }
}

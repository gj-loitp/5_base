package vn.loitp.app.activity.customviews.layout.shadowlayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.core.base.BaseFontActivity
import com.utils.util.ConvertUtils
import kotlinx.android.synthetic.main.activity_shadow_layout.*
import vn.loitp.app.R

//https://github.com/lijiankun24/ShadowLayout
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

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shadow_layout
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvChangeOval -> slOval.setShadowColor(ContextCompat.getColor(this, R.color.black50))
            R.id.tvChangeRadius -> slRectangle.setShadowColor(Color.parseColor("#EE00FF7F"))
            R.id.tvChangeRectangle -> slRadius.setShadowRadius(ConvertUtils.dp2px(12f).toFloat())
        }
    }
}

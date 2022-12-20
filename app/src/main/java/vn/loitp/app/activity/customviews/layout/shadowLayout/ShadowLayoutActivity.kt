package vn.loitp.app.activity.customviews.layout.shadowLayout

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.utils.util.ConvertUtils
import kotlinx.android.synthetic.main.activity_shadow_layout.*
import vn.loitp.app.R

@LogTag("ShadowLayoutActivity")
@IsFullScreen(false)
class ShadowLayoutActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shadow_layout
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/lijiankun24/ShadowLayout"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ShadowLayoutActivity::class.java.simpleName
        }
        tvChangeOval.setOnClickListener(this)
        tvChangeRadius.setOnClickListener(this)
        tvChangeRectangle.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            tvChangeOval -> slOval.setShadowColor(LAppResource.getColor(R.color.black50))
            tvChangeRadius -> slRectangle.setShadowColor(Color.parseColor("#EE00FF7F"))
            tvChangeRectangle -> slRadius.setShadowRadius(ConvertUtils.dp2px(12f).toFloat())
        }
    }
}

package vn.loitp.app.activity.customviews.recyclerview.arcView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.amir.arcview.ArcLinearLayout
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_arc_view.*
import kotlinx.android.synthetic.main.include_arc_button.*
import vn.loitp.app.R

@LogTag("ArcViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class ArcViewActivity : BaseFontActivity(), View.OnClickListener {
    private lateinit var strokeArc: ArcLinearLayout
    private lateinit var shadowArc: ArcLinearLayout

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_arc_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/amir5121/arcView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ArcViewActivity::class.java.simpleName
        }

        kick_me.setOnClickListener(this)
        kick_swapped.setOnClickListener(this)
        include_buttons_stroke.setOnClickListener(this)
        include_buttons_shadow.setOnClickListener(this)
        strokeArc =
            layoutInflater.inflate(
                R.layout.stroke_arc_linear_layout,
                include_arc_buttons_temp_arc,
                false
            ) as ArcLinearLayout
        shadowArc =
            layoutInflater.inflate(
                R.layout.shadow_arc_linear_layout,
                include_arc_buttons_temp_arc,
                false
            ) as ArcLinearLayout
    }

    override fun onClick(v: View?) {
        when (v) {
            kick_me -> {
                if (include_buttons_scroll_view.isKnockedIn) {
                    include_buttons_scroll_view.knockout()
                } else {
                    include_buttons_scroll_view.knockIn()
                }
            }
            kick_swapped -> {
                include_arc_buttons_temp_arc.swapView(null)
            }
            include_buttons_shadow -> {
                include_arc_buttons_temp_arc.swapView(shadowArc)
            }
            include_buttons_stroke -> {
                include_arc_buttons_temp_arc.swapView(strokeArc)
            }
        }
    }
}

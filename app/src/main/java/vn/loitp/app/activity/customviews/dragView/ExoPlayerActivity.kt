package vn.loitp.app.activity.customviews.dragView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.toPx
import kotlinx.android.synthetic.main.activity_drag_view_custom.*
import kotlinx.android.synthetic.main.layout_drag_view_bottom.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dragView.fragment.BottomFragment
import vn.loitp.app.activity.customviews.dragView.fragment.ExoPlayerTopFragment
import kotlin.math.max
import kotlin.math.min

@LogTag("ExoPlayerActivity")
@IsFullScreen(false)
class ExoPlayerActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_drag_view_custom
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ExoPlayerActivity::class.java.simpleName
        }

        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
            }

            override fun onChangePercent(percent: Float) {
                alpha.alpha = 1 - percent
                shadow.alpha = percent
            }
        })

        supportFragmentManager.beginTransaction().add(R.id.frameTop, ExoPlayerTopFragment())
            .commit()
        supportFragmentManager.beginTransaction().add(R.id.frameBottom, BottomFragment()).commit()

        btnMax.setSafeOnClickListener { dragView.maximize() }
        btnMin.setSafeOnClickListener { dragView.minimize() }
        btnClose.setSafeOnClickListener { dragView.close() }

        btnSetHeightMax.setSafeOnClickListener {
            var heightMax = 0
            if (etHeightMax.text?.isNotEmpty() == true) {
                heightMax = etHeightMax.text.toString().toInt()
            }
            heightMax = max(heightMax, 200)
            heightMax = min(heightMax, 400)

            dragView.setHeightMax(heightMax.toPx(), true)
        }
    }
}

package vn.loitp.app.activity.customviews.dragView

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.tuanhav95.drag.DragView
import com.tuanhav95.drag.utils.toPx
import kotlinx.android.synthetic.main.activity_drag_view_custom.*
import kotlinx.android.synthetic.main.layout_drag_view_bottom.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dragView.fragment.BottomFragment
import vn.loitp.app.activity.customviews.dragView.fragment.NormalTopFragment
import kotlin.math.max
import kotlin.math.min

@LogTag("DragViewCustomActivity")
@IsFullScreen(false)
class DragViewCustomActivity : BaseFontActivity() {

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
            this.tvTitle?.text = DragViewCustomActivity::class.java.simpleName
        }

        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
            }

            override fun onChangePercent(percent: Float) {
                alpha.alpha = 1 - percent
                shadow.alpha = percent
            }
        })

        supportFragmentManager.beginTransaction().add(R.id.frameTop, NormalTopFragment()).commit()
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

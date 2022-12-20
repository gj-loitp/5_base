package vn.loitp.app.activity.customviews.dragView

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.activity_drag_view_normal.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dragView.fragment.BottomFragment
import vn.loitp.app.activity.customviews.dragView.fragment.NormalTopFragment

@LogTag("NormalActivity")
@IsFullScreen(false)
class NormalActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_drag_view_normal
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
            this.tvTitle?.text = NormalActivity::class.java.simpleName
        }
        dragView.setDragListener(object : DragView.DragListener {
            override fun onChangeState(state: DragView.State) {
            }

            override fun onChangePercent(percent: Float) {
                alpha.alpha = 1 - percent
            }
        })

        supportFragmentManager.beginTransaction().add(R.id.frameFirst, NormalTopFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.frameSecond, BottomFragment()).commit()

        btnMax.setSafeOnClickListener { dragView.maximize() }
        btnMin.setSafeOnClickListener { dragView.minimize() }
        btnClose.setSafeOnClickListener { dragView.close() }
    }
}

package vn.loitp.app.activity.customviews.dragview

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.views.setSafeOnClickListener
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.activity_drag_view_normal.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dragview.fragment.BottomFragment
import vn.loitp.app.activity.customviews.dragview.fragment.NormalTopFragment

@LogTag("MenuCustomViewsActivity")
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

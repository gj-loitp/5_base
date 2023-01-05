package vn.loitp.a.cv.dragView

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.tuanhav95.drag.DragView
import kotlinx.android.synthetic.main.a_drag_view_normal.*
import vn.loitp.R
import vn.loitp.a.cv.dragView.frm.BottomFragment
import vn.loitp.a.cv.dragView.frm.NormalTopFragment

@LogTag("NormalActivity")
@IsFullScreen(false)
class NormalActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_drag_view_normal
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
            this.tvTitle?.text = NormalActivityFont::class.java.simpleName
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

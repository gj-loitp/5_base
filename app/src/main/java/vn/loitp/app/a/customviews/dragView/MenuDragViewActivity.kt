package vn.loitp.app.a.customviews.dragView

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_drag_view.*
import vn.loitp.R

@LogTag("MenuDragViewActivity")
@IsFullScreen(false)
class MenuDragViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_drag_view
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuDragViewActivity::class.java.simpleName
        }
        btnNormal.setSafeOnClickListener {
            startActivity(Intent(this, NormalActivity::class.java))
            LActivityUtil.tranIn(this)
        }
        btnCustom.setSafeOnClickListener {
            startActivity(Intent(this, DragViewCustomActivity::class.java))
            LActivityUtil.tranIn(this)
        }
        btnExoplayer.setSafeOnClickListener {
            startActivity(Intent(this, ExoPlayerActivity::class.java))
            LActivityUtil.tranIn(this)
        }
    }
}

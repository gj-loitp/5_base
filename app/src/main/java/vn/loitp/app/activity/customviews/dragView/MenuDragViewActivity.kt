package vn.loitp.app.activity.customviews.dragView

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_drag_view.*
import vn.loitp.app.R

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
            this.viewShadow?.isVisible = true
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

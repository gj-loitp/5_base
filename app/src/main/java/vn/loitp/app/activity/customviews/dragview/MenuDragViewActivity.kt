package vn.loitp.app.activity.customviews.dragview

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_menu_drag_view.*
import vn.loitp.app.R

@LogTag("MenuCustomViewsActivity")
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
        btnNormal.setSafeOnClickListener {
            startActivity(Intent(this, NormalActivity::class.java))
            LActivityUtil.tranIn(this)
        }
        btnCustom.setSafeOnClickListener {
            startActivity(Intent(this, CustomActivity::class.java))
            LActivityUtil.tranIn(this)
        }
        btnExoplayer.setSafeOnClickListener {
            startActivity(Intent(this, ExoPlayerActivity::class.java))
            LActivityUtil.tranIn(this)
        }
    }
}

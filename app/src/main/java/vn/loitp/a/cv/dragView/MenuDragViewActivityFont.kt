package vn.loitp.a.cv.dragView

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_menu_drag_view.*
import vn.loitp.R

@LogTag("MenuDragViewActivity")
@IsFullScreen(false)
class MenuDragViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_drag_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuDragViewActivityFont::class.java.simpleName
        }
        btnNormal.setSafeOnClickListener {
            startActivity(Intent(this, NormalActivityFont::class.java))
            this.tranIn()
        }
        btnCustom.setSafeOnClickListener {
            startActivity(Intent(this, DragViewCustomActivityFont::class.java))
            this.tranIn()
        }
        btnExoplayer.setSafeOnClickListener {
            startActivity(Intent(this, ExoPlayerActivityFont::class.java))
            this.tranIn()
        }
    }
}

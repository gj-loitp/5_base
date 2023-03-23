package vn.loitp.up.a.cv.dragView

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import vn.loitp.R
import vn.loitp.databinding.AMenuDragViewBinding

@LogTag("MenuDragViewActivity")
@IsFullScreen(false)
class MenuDragViewActivity : BaseActivityFont() {
    private lateinit var binding: AMenuDragViewBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_drag_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuDragViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuDragViewActivity::class.java.simpleName
        }
        binding.btnNormal.setSafeOnClickListener {
            startActivity(Intent(this, NormalActivity::class.java))
            this.tranIn()
        }
        binding.btnCustom.setSafeOnClickListener {
            startActivity(Intent(this, DragViewCustomActivity::class.java))
            this.tranIn()
        }
        binding.btnExoplayer.setSafeOnClickListener {
            startActivity(Intent(this, ExoPlayerActivity::class.java))
            this.tranIn()
        }
    }
}

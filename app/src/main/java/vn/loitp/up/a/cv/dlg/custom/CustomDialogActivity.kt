package vn.loitp.up.a.cv.dlg.custom

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.toggleFullscreen
import vn.loitp.R
import vn.loitp.databinding.ADlgCustomBinding

@LogTag("CustomDialogActivity")
@IsFullScreen(false)
class CustomDialogActivity : BaseActivityFont() {

    private var isFullScreen: Boolean = false
    private lateinit var binding: ADlgCustomBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADlgCustomBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CustomDialogActivity::class.java.simpleName
        }
        binding.btToggleFullScreen.setSafeOnClickListener {
            isFullScreen = !isFullScreen
            this.toggleFullscreen(isFullScreen = isFullScreen)
        }
        binding.btDefault.setSafeOnClickListener {
            PositionDialog().showImmersivePos(
                activity = this,
                anchorView = binding.btAnchorView,
                sizeWidthPx = null,
                sizeHeightPx = null,
            )
        }
    }
}

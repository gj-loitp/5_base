package vn.loitp.up.a.func.fullScreen

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.toggleFullscreen
import com.loitp.views.dlg.imersiveDialog.ImmersiveDialogFragment
import vn.loitp.R
import vn.loitp.databinding.AFuncFullscreenBinding

@LogTag("FullScreenActivity")
@IsFullScreen(false)
class FullScreenActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AFuncFullscreenBinding
    private var isFullScreen = false

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFuncFullscreenBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = FullScreenActivity::class.java.simpleName
        }
        binding.btToggleFullScreen.setOnClickListener(this)
        binding.btShowDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btToggleFullScreen -> {
                isFullScreen = !isFullScreen
                this.toggleFullscreen(isFullScreen)
            }
            binding.btShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        ImmersiveDialogFragment().showImmersive(this)
    }
}

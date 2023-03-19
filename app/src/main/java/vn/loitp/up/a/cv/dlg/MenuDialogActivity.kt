package vn.loitp.up.a.cv.dlg

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.cv.dlg.custom.CustomDialogActivityFont
import vn.loitp.a.cv.dlg.customProgress.CustomProgressDialogActivityFont
import vn.loitp.a.cv.dlg.original.DialogOriginalActivityFont
import vn.loitp.a.cv.dlg.pretty.PrettyDialogActivityFont
import vn.loitp.a.cv.dlg.slideImages.DialogSlideImagesActivityFont
import vn.loitp.databinding.ADlgMenuBinding

@LogTag("MenuDialogActivity")
@IsFullScreen(false)
class MenuDialogActivity : BaseActivityFont() {
    private lateinit var binding: ADlgMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADlgMenuBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuDialogActivity::class.java.simpleName
        }
        binding.btOriginalDialog.setSafeOnClickListener {
            launchActivity(DialogOriginalActivityFont::class.java)
        }
        binding.btPrettyDialog.setSafeOnClickListener {
            launchActivity(PrettyDialogActivityFont::class.java)
        }
        binding.btCustomProgressDialog.setSafeOnClickListener {
            launchActivity(CustomProgressDialogActivityFont::class.java)
        }
        binding.btSlideImages.setSafeOnClickListener {
            launchActivity(DialogSlideImagesActivityFont::class.java)
        }
        binding.btCustomDialog.setSafeOnClickListener {
            launchActivity(CustomDialogActivityFont::class.java)
        }
    }
}

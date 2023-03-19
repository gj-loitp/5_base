package vn.loitp.up.a.cv.dlg

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.up.a.cv.dlg.custom.CustomDialogActivity
import vn.loitp.up.a.cv.dlg.customProgress.CustomProgressDialogActivity
import vn.loitp.up.a.cv.dlg.original.DialogOriginalActivity
import vn.loitp.up.a.cv.dlg.pretty.PrettyDialogActivity
import vn.loitp.up.a.cv.dlg.slideImages.DialogSlideImagesActivity
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
            launchActivity(DialogOriginalActivity::class.java)
        }
        binding.btPrettyDialog.setSafeOnClickListener {
            launchActivity(PrettyDialogActivity::class.java)
        }
        binding.btCustomProgressDialog.setSafeOnClickListener {
            launchActivity(CustomProgressDialogActivity::class.java)
        }
        binding.btSlideImages.setSafeOnClickListener {
            launchActivity(DialogSlideImagesActivity::class.java)
        }
        binding.btCustomDialog.setSafeOnClickListener {
            launchActivity(CustomDialogActivity::class.java)
        }
    }
}

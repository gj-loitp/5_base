package vn.loitp.up.a.cv.dlg.customProgress

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ADlgCustomProgressBinding

@LogTag("CustomProgressDialogActivity")
@IsFullScreen(false)
class CustomProgressDialogActivity : BaseActivityFont() {
    private lateinit var binding: ADlgCustomProgressBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorBkgProgressDialog = R.color.deepPink

        binding = ADlgCustomProgressBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = CustomProgressDialogActivity::class.java.simpleName
        }
        binding.bt0.setSafeOnClickListener {
            showDialogProgress()
            setDelay(mls = 4000, runnable = {
                hideDialogProgress()
            })
        }
    }
}

package vn.loitp.up.a.cv.popupMenu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showPopup
import vn.loitp.R
import vn.loitp.databinding.AMenuPopupBinding

@LogTag("PopupMenuActivity")
@IsFullScreen(false)
class PopupMenuActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuPopupBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuPopupBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = PopupMenuActivity::class.java.simpleName
        }
        binding.btShow1.setOnClickListener(this)
        binding.btShow2.setOnClickListener(this)
        binding.btShow3.setOnClickListener(this)
        binding.btShow4.setOnClickListener(this)
        binding.btShow5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btShow1,
            binding.btShow2,
            binding.btShow3,
            binding.btShow4,
            binding.btShow5 -> {
                this.showPopup(
                    showOnView = v,
                    menuRes = R.menu.menu_popup,
                    callback = { menuItem ->
                        showShortInformation(menuItem.title.toString())
                    }
                )
            }
        }
    }
}

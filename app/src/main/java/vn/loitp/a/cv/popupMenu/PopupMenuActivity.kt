package vn.loitp.a.cv.popupMenu

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showPopup
import kotlinx.android.synthetic.main.a_menu_popup.*
import vn.loitp.R

@LogTag("PopupMenuActivity")
@IsFullScreen(false)
class PopupMenuActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_popup
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
            this.tvTitle?.text = PopupMenuActivity::class.java.simpleName
        }
        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShow4.setOnClickListener(this)
        btShow5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btShow1,
            btShow2,
            btShow3,
            btShow4,
            btShow5 -> {
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

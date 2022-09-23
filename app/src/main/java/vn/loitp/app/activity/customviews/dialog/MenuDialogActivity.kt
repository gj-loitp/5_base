package vn.loitp.app.activity.customviews.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_dialog.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dialog.customDialog.CustomDialogActivity
import vn.loitp.app.activity.customviews.dialog.customProgressDialog.CustomProgressDialogActivity
import vn.loitp.app.activity.customviews.dialog.original.DialogOriginalActivity
import vn.loitp.app.activity.customviews.dialog.prettyDialog.PrettyDialogActivity
import vn.loitp.app.activity.customviews.dialog.slideImages.DialogSlideImagesActivity

@LogTag("MenuDialogActivity")
@IsFullScreen(false)
class MenuDialogActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuDialogActivity::class.java.simpleName
        }
        btOriginalDialog.setOnClickListener(this)
        btPrettyDialog.setOnClickListener(this)
        btCustomProgressDialog.setOnClickListener(this)
        btSlideImages.setOnClickListener(this)
        btCustomDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btOriginalDialog -> intent = Intent(this, DialogOriginalActivity::class.java)
            btPrettyDialog -> intent = Intent(this, PrettyDialogActivity::class.java)
            btCustomProgressDialog ->
                intent =
                    Intent(this, CustomProgressDialogActivity::class.java)
            btSlideImages -> intent = Intent(this, DialogSlideImagesActivity::class.java)
            btCustomDialog -> intent = Intent(this, CustomDialogActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}

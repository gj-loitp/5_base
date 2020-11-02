package vn.loitp.app.activity.customviews.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_dialog_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.dialog.customdialog.CustomDialogActivity
import vn.loitp.app.activity.customviews.dialog.customprogressdialog.CustomProgressDialoglActivity
import vn.loitp.app.activity.customviews.dialog.iosdialog.DialogIOSActivity
import vn.loitp.app.activity.customviews.dialog.originaldialog.DialogOriginalActivity
import vn.loitp.app.activity.customviews.dialog.prettydialog.PrettyDialogActivity
import vn.loitp.app.activity.customviews.dialog.slideimages.DialogSlideImagesActivity
import vn.loitp.app.activity.customviews.dialog.swipeawaydialog.SwipeAwayDialogActivity

@LogTag("DialogMenuActivity")
@IsFullScreen(false)
class DialogMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btIosDialog.setOnClickListener(this)
        btOriginalDialog.setOnClickListener(this)
        btPrettyDialog.setOnClickListener(this)
        btSwipeAwayDialog.setOnClickListener(this)
        btCustomProgressDialog.setOnClickListener(this)
        btSlideImages.setOnClickListener(this)
        btCustomDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btIosDialog -> intent = Intent(this, DialogIOSActivity::class.java)
            btOriginalDialog -> intent = Intent(this, DialogOriginalActivity::class.java)
            btPrettyDialog -> intent = Intent(this, PrettyDialogActivity::class.java)
            btSwipeAwayDialog -> intent = Intent(this, SwipeAwayDialogActivity::class.java)
            btCustomProgressDialog -> intent = Intent(this, CustomProgressDialoglActivity::class.java)
            btSlideImages -> intent = Intent(this, DialogSlideImagesActivity::class.java)
            btCustomDialog -> intent = Intent(this, CustomDialogActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}

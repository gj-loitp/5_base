package vn.loitp.app.activity.customviews.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
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

class DialogMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btIosDialog.setOnClickListener(this)
        btOriginalDialog.setOnClickListener(this)
        btPrettyDialog.setOnClickListener(this)
        btSwipeAwayDialog.setOnClickListener(this)
        btCustomProgressDialog.setOnClickListener(this)
        btSlideImages.setOnClickListener(this)
        btCustomDialog.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dialog_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btIosDialog -> intent = Intent(activity, DialogIOSActivity::class.java)
            btOriginalDialog -> intent = Intent(activity, DialogOriginalActivity::class.java)
            btPrettyDialog -> intent = Intent(activity, PrettyDialogActivity::class.java)
            btSwipeAwayDialog -> intent = Intent(activity, SwipeAwayDialogActivity::class.java)
            btCustomProgressDialog -> intent = Intent(activity, CustomProgressDialoglActivity::class.java)
            btSlideImages -> intent = Intent(activity, DialogSlideImagesActivity::class.java)
            btCustomDialog -> intent = Intent(activity, CustomDialogActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}

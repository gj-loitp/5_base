package vn.loitp.app.activity.customviews.dialog

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.dialog.customprogressdialog.CustomProgressDialoglActivity
import vn.loitp.app.activity.customviews.dialog.iosdialog.DialogIOSActivity
import vn.loitp.app.activity.customviews.dialog.originaldialog.DialogOriginalActivity
import vn.loitp.app.activity.customviews.dialog.prettydialog.PrettyDialogActivity
import vn.loitp.app.activity.customviews.dialog.swipeawaydialog.SwipeAwayDialogActivity

class DialogMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_ios_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_original_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_pretty_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_swipe_away_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_custom_progress_dialog).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_dialog
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_ios_dialog -> intent = Intent(activity, DialogIOSActivity::class.java)
            R.id.bt_original_dialog -> intent = Intent(activity, DialogOriginalActivity::class.java)
            R.id.bt_pretty_dialog -> intent = Intent(activity, PrettyDialogActivity::class.java)
            R.id.bt_swipe_away_dialog -> intent = Intent(activity, SwipeAwayDialogActivity::class.java)
            R.id.bt_custom_progress_dialog -> intent = Intent(activity, CustomProgressDialoglActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}

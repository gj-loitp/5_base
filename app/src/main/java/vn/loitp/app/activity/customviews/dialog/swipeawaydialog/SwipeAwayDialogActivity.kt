package vn.loitp.app.activity.customviews.dialog.swipeawaydialog

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/kakajika/SwipeAwayDialog
class SwipeAwayDialogActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_show_1).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_2).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_3).setOnClickListener(this)
        findViewById<View>(R.id.bt_show_list).setOnClickListener(this)
        findViewById<View>(R.id.bt_progress_dialog).setOnClickListener(this)
        findViewById<View>(R.id.bt_custom_dialog).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_swipe_away_dialog
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_show_1 -> show(SADilog.KEY_1)
            R.id.bt_show_2 -> show(SADilog.KEY_2)
            R.id.bt_show_3 -> show(SADilog.KEY_3)
            R.id.bt_show_list -> show(SADilog.KEY_4)
            R.id.bt_progress_dialog -> show(SADilog.KEY_5)
            R.id.bt_custom_dialog -> show(SADilog.KEY_6)
        }
    }

    private fun show(key: Int) {
        val saDilog = SADilog()
        val bundle = Bundle()
        bundle.putInt(SADilog.KEY, key)
        saDilog.arguments = bundle
        saDilog.show(supportFragmentManager, SADilog::class.java.simpleName)
    }
}

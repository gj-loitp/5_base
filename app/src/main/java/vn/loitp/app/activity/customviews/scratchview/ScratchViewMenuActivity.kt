package vn.loitp.app.activity.customviews.scratchview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.scratchview.scratchviewimage.ScratchViewImageActivity
import vn.loitp.app.activity.customviews.scratchview.scratchviewtext.ScratchViewTextActivity

class ScratchViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_scratchview_image).setOnClickListener(this)
        findViewById<View>(R.id.bt_scratchview_text).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_scratchview
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_scratchview_image -> intent = Intent(activity, ScratchViewImageActivity::class.java)
            R.id.bt_scratchview_text -> intent = Intent(activity, ScratchViewTextActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}

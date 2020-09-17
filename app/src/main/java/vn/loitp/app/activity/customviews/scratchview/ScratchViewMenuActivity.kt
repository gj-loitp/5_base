package vn.loitp.app.activity.customviews.scratchview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_scratchview_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.scratchview.scratchviewimage.ScratchViewImageActivity
import vn.loitp.app.activity.customviews.scratchview.scratchviewtext.ScratchViewTextActivity

@LayoutId(R.layout.activity_scratchview_menu)
class ScratchViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btScratchViewImage.setOnClickListener(this)
        btScratchViewText.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btScratchViewImage -> intent = Intent(activity, ScratchViewImageActivity::class.java)
            btScratchViewText -> intent = Intent(activity, ScratchViewTextActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}

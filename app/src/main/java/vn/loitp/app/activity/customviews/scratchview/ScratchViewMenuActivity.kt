package vn.loitp.app.activity.customviews.scratchview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_scratchview_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.scratchview.scratchviewimage.ScratchViewImageActivity
import vn.loitp.app.activity.customviews.scratchview.scratchviewtext.ScratchViewTextActivity

@LogTag("ScratchViewMenuActivity")
@IsFullScreen(false)
class ScratchViewMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scratchview_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btScratchViewImage.setOnClickListener(this)
        btScratchViewText.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btScratchViewImage -> intent = Intent(this, ScratchViewImageActivity::class.java)
            btScratchViewText -> intent = Intent(this, ScratchViewTextActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}

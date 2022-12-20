package vn.loitp.app.activity.customviews.scratchView

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_scratch_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.scratchView.scratchViewImage.ScratchViewImageActivity
import vn.loitp.app.activity.customviews.scratchView.scratchViewText.ScratchViewTextActivity

@LogTag("ScratchViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuScratchViewActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_scratch_view
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
            this.tvTitle?.text = MenuScratchViewActivity::class.java.simpleName
        }
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

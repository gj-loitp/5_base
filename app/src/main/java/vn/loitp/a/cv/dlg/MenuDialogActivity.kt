package vn.loitp.a.cv.dlg

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_dlg_menu.*
import vn.loitp.R
import vn.loitp.a.cv.dlg.custom.CustomDialogActivity
import vn.loitp.a.cv.dlg.customProgress.CustomProgressDialogActivity
import vn.loitp.a.cv.dlg.original.DialogOriginalActivity
import vn.loitp.a.cv.dlg.pretty.PrettyDialogActivity
import vn.loitp.a.cv.dlg.slideImages.DialogSlideImagesActivity

@LogTag("MenuDialogActivity")
@IsFullScreen(false)
class MenuDialogActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_dlg_menu
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

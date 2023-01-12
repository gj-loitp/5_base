package vn.loitp.a.cv.dlg

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_dlg_menu.*
import vn.loitp.R
import vn.loitp.a.cv.dlg.custom.CustomDialogActivityFont
import vn.loitp.a.cv.dlg.customProgress.CustomProgressDialogActivityFont
import vn.loitp.a.cv.dlg.original.DialogOriginalActivityFont
import vn.loitp.a.cv.dlg.pretty.PrettyDialogActivityFont
import vn.loitp.a.cv.dlg.slideImages.DialogSlideImagesActivityFont

@LogTag("MenuDialogActivity")
@IsFullScreen(false)
class MenuDialogActivity : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_dlg_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
            btOriginalDialog -> intent = Intent(this, DialogOriginalActivityFont::class.java)
            btPrettyDialog -> intent = Intent(this, PrettyDialogActivityFont::class.java)
            btCustomProgressDialog ->
                intent =
                    Intent(this, CustomProgressDialogActivityFont::class.java)
            btSlideImages -> intent = Intent(this, DialogSlideImagesActivityFont::class.java)
            btCustomDialog -> intent = Intent(this, CustomDialogActivityFont::class.java)
        }
        intent?.let {
            startActivity(intent)
            this.tranIn()
        }
    }
}

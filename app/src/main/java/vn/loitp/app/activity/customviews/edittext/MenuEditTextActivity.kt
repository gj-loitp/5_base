package vn.loitp.app.activity.customviews.edittext

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_edittext.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.edittext.animatedExpandable.AnimatedExpandableEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autoResize.AutoResizeEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autoSuggest.EditTextAutoSuggestActivity
import vn.loitp.app.activity.customviews.edittext.currency.CurrencyEditTextActivity
import vn.loitp.app.activity.customviews.edittext.lEdittext.LEditTextActivity
import vn.loitp.app.activity.customviews.edittext.materialTextField.MaterialTextFieldActivity
import vn.loitp.app.activity.customviews.edittext.textWatcher.EditTextTextWatcherActivity

@LogTag("EditTextMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuEditTextActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_edittext
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuEditTextActivity::class.java.simpleName
        }
        btAnimatedExpandableEditText.setOnClickListener(this)
        btAutoResizeEditText.setOnClickListener(this)
        btMaterialTextField.setOnClickListener(this)
        btAutoSuggestEditText.setOnClickListener(this)
        btLEditText.setOnClickListener(this)
        btEditextTextWatcher.setOnClickListener(this)
        btCurrencyEditText.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAnimatedExpandableEditText ->
                intent =
                    Intent(this, AnimatedExpandableEditTextActivity::class.java)
            btAutoResizeEditText -> intent = Intent(this, AutoResizeEditTextActivity::class.java)
            btMaterialTextField -> intent = Intent(this, MaterialTextFieldActivity::class.java)
            btAutoSuggestEditText -> intent = Intent(this, EditTextAutoSuggestActivity::class.java)
            btLEditText -> intent = Intent(this, LEditTextActivity::class.java)
            btEditextTextWatcher -> intent = Intent(this, EditTextTextWatcherActivity::class.java)
            btCurrencyEditText -> intent = Intent(this, CurrencyEditTextActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
            this
        }
    }
}

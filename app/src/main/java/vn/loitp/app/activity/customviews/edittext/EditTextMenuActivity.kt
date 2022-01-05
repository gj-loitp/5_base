package vn.loitp.app.activity.customviews.edittext

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_edittext_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext.AnimatedExpandableEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autoresizeedittext.AutoResizeEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autosuggest.EditTextAutoSuggestActivity
import vn.loitp.app.activity.customviews.edittext.currencyedittext.CurrencyEditTextActivity
import vn.loitp.app.activity.customviews.edittext.ledittext.LEditTextActivity
import vn.loitp.app.activity.customviews.edittext.materialtextfield.MaterialTextFieldActivity
import vn.loitp.app.activity.customviews.edittext.textwatcher.EditTextTextWatcherActivity

@LogTag("EditTextMenuActivity")
@IsFullScreen(false)
class EditTextMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_edittext_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

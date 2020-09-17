package vn.loitp.app.activity.customviews.edittext

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_edittext_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext.AnimatedExpandableEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autoresizeedittext.AutoResizeEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autosuggest.EditTextAutoSuggestActivity
import vn.loitp.app.activity.customviews.edittext.biuedittext.BiuEditTextActivity
import vn.loitp.app.activity.customviews.edittext.currencyedittext.CurrencyEditTextActivity
import vn.loitp.app.activity.customviews.edittext.ledittext.LEditTextActivity
import vn.loitp.app.activity.customviews.edittext.materialtextfield.MaterialTextFieldActivity
import vn.loitp.app.activity.customviews.edittext.textwatcher.EditTextTextWatcherActivity

@LayoutId(R.layout.activity_edittext_menu)
class EditTextMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btAnimatedExpandableEditText.setOnClickListener(this)
        btAutoResizeEditText.setOnClickListener(this)
        btMaterialTextField.setOnClickListener(this)
        btBiuEditText.setOnClickListener(this)
        btAutoSuggestEditText.setOnClickListener(this)
        btLEditText.setOnClickListener(this)
        btEditextTextWatcher.setOnClickListener(this)
        btCurrencyEditText.setOnClickListener(this)
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
            btAnimatedExpandableEditText -> intent = Intent(activity, AnimatedExpandableEditTextActivity::class.java)
            btAutoResizeEditText -> intent = Intent(activity, AutoResizeEditTextActivity::class.java)
            btMaterialTextField -> intent = Intent(activity, MaterialTextFieldActivity::class.java)
            btBiuEditText -> intent = Intent(activity, BiuEditTextActivity::class.java)
            btAutoSuggestEditText -> intent = Intent(activity, EditTextAutoSuggestActivity::class.java)
            btLEditText -> intent = Intent(activity, LEditTextActivity::class.java)
            btEditextTextWatcher -> intent = Intent(activity, EditTextTextWatcherActivity::class.java)
            btCurrencyEditText -> intent = Intent(activity, CurrencyEditTextActivity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}

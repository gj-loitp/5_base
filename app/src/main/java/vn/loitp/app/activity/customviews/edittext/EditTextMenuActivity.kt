package vn.loitp.app.activity.customviews.edittext

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext.AnimatedExpandableEditTextActivity
import vn.loitp.app.activity.customviews.edittext.autoresizeedittext.AutoResizeEditTextActivity
import vn.loitp.app.activity.customviews.edittext.biuedittext.BiuEditTextActivity
import vn.loitp.app.activity.customviews.edittext.materialtextfield.MaterialTextFieldActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class EditTextMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_animated_expandable_edit_text).setOnClickListener(this)
        findViewById<View>(R.id.bt_auto_resize_edit_text).setOnClickListener(this)
        findViewById<View>(R.id.bt_material_text_field).setOnClickListener(this)
        findViewById<View>(R.id.bt_biu_edit_text).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_edittext
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_animated_expandable_edit_text -> intent = Intent(activity, AnimatedExpandableEditTextActivity::class.java)
            R.id.bt_auto_resize_edit_text -> intent = Intent(activity, AutoResizeEditTextActivity::class.java)
            R.id.bt_material_text_field -> intent = Intent(activity, MaterialTextFieldActivity::class.java)
            R.id.bt_biu_edit_text -> intent = Intent(activity, BiuEditTextActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}

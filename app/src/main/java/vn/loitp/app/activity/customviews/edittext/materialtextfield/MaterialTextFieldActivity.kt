package vn.loitp.app.activity.customviews.edittext.materialtextfield

import android.os.Bundle
import com.core.base.BaseActivity
import loitp.basemaster.R

//guide https://github.com/florent37/MaterialTextField
class MaterialTextFieldActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_material_textfield
    }

}

package vn.loitp.app.activity.customviews.edittext.materialtextfield

import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//guide https://github.com/florent37/MaterialTextField

@LayoutId(R.layout.activity_editext_material_textfield)
@LogTag("MaterialTextFieldActivity")
class MaterialTextFieldActivity : BaseFontActivity() {

    override fun setFullScreen(): Boolean {
        return false
    }

}

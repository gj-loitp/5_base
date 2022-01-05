package vn.loitp.app.activity.customviews.edittext.materialtextfield

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

// guide https://github.com/florent37/MaterialTextField

@LogTag("MaterialTextFieldActivity")
@IsFullScreen(false)
class MaterialTextFieldActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_material_textfield
    }
}

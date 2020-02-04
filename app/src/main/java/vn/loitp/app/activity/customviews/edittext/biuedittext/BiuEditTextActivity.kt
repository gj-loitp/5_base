package vn.loitp.app.activity.customviews.edittext.biuedittext

import android.os.Bundle
import com.core.base.BaseActivity
import vn.loitp.app.R

//guide https://github.com/xujinyang/BiuEditText
class BiuEditTextActivity : BaseActivity() {
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
        return R.layout.activity_biu_editext
    }

}

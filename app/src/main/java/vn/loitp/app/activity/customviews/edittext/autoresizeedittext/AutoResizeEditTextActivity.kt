package vn.loitp.app.activity.customviews.edittext.autoresizeedittext

import android.os.Bundle

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity

class AutoResizeEditTextActivity : BaseFontActivity() {
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
        return R.layout.activity_autoresize_editext
    }
}

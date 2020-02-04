package vn.loitp.app.activity.demo.deeplinks

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_deep_link.*
import vn.loitp.app.R

class DeepLinksActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action = intent?.action
        val data = intent?.data
        tvDeepLinks.text = "action: $action\ndata: $data"
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_deep_link
    }
}

package vn.loitp.app.activity.demo.deeplinks

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_demo_deep_link.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_deep_link)
@LogTag("DeepLinksActivity")
@IsFullScreen(false)
class DeepLinksActivity : BaseFontActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val action = intent?.action
        val data = intent?.data
        tvDeepLinks.text = "action: $action\ndata: $data"
    }
}

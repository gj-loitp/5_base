package vn.loitp.app.activity.demo.ndk

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_demo_ndk.*
import vn.loitp.app.R

@LogTag("NDKDemoActivity")
@IsFullScreen(false)
class NDKDemoActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_ndk
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("keys")
        setupViews()
    }

    private external fun getStringHello(): String

    private fun setupViews() {
        tv.text = getStringHello()
    }

}

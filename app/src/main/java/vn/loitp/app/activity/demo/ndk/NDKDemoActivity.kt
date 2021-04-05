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

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_ndk
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
    }

    private external fun getStringHello(s: String): String
    private external fun add(x: Int, y: Int): Int

    private fun setupViews() {
        val s = getStringHello("Hello " + System.nanoTime()) + "\n" + add(1, 2);
        tv.text = s
    }

}

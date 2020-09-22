package vn.loitp.app.activity.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_tutorial_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.retrofit2.Retrofit2Activity
import vn.loitp.app.activity.tutorial.rxjava2.MenuRxJava2Activity

@LayoutId(R.layout.activity_tutorial_menu)
@LogTag("MenuTutorialActivity")
@IsFullScreen(false)
class MenuTutorialActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        btRxJava2.setOnClickListener(this)
        btRetrofit2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btRxJava2 -> intent = Intent(this, MenuRxJava2Activity::class.java)
            btRetrofit2 -> intent = Intent(this, Retrofit2Activity::class.java)
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}

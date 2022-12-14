package vn.loitp.app.activity.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_tutorial.*
import vn.loitp.app.R
import vn.loitp.app.activity.tutorial.retrofit2.Retrofit2Activity
import vn.loitp.app.activity.tutorial.rxjava2.MenuRxJava2Activity

@LogTag("MenuTutorialActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuTutorialActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_tutorial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuTutorialActivity::class.java.simpleName
        }
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

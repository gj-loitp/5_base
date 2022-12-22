package vn.loitp.app.activity.tutorial

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_tutorial.*
import vn.loitp.R
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
        when (v) {
            btRxJava2 -> launchActivity(MenuRxJava2Activity::class.java)
            btRetrofit2 -> launchActivity(Retrofit2Activity::class.java)
        }
    }
}

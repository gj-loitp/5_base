package vn.loitp.app.activity.customviews.button

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_button.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.button.autoSizeButton.AutoSizeButtonActivity
import vn.loitp.app.activity.customviews.button.circularImageClick.CircularImageClickActivity
import vn.loitp.app.activity.customviews.button.circularProgressButton.CPBActivity
import vn.loitp.app.activity.customviews.button.fab.FabActivity
import vn.loitp.app.activity.customviews.button.fitButton.FitButtonActivity
import vn.loitp.app.activity.customviews.button.goodView.GoodViewActivity
import vn.loitp.app.activity.customviews.button.lButton.LButtonActivity
import vn.loitp.app.activity.customviews.button.qButton.QButtonActivity
import vn.loitp.app.activity.customviews.button.shineButton.ShineButtonActivity

@LogTag("MenuButtonActivity")
@IsFullScreen(false)
class MenuButtonActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_button
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuButtonActivity::class.java.simpleName
        }
        btShineButton.setOnClickListener(this)
        btCircularImageClick.setOnClickListener(this)
        btGoodView.setOnClickListener(this)
        btlButton.setOnClickListener(this)
        btAutoSizeButton.setOnClickListener(this)
        btQButton.setOnClickListener(this)
        btFab.setOnClickListener(this)
        btFitButtonActivity.setOnClickListener(this)
        btCircularProgressButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btShineButton -> launchActivity(ShineButtonActivity::class.java)
            btCircularImageClick -> launchActivity(CircularImageClickActivity::class.java)
            btGoodView -> launchActivity(GoodViewActivity::class.java)
            btlButton -> launchActivity(LButtonActivity::class.java)
            btAutoSizeButton -> launchActivity(AutoSizeButtonActivity::class.java)
            btQButton -> launchActivity(QButtonActivity::class.java)
            btFab -> launchActivity(FabActivity::class.java)
            btFitButtonActivity -> launchActivity(FitButtonActivity::class.java)
            btCircularProgressButton -> launchActivity(CPBActivity::class.java)
        }
    }
}

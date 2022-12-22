package vn.loitp.a.cv.bt

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_button.*
import vn.loitp.R
import vn.loitp.a.cv.bt.autoSize.AutoSizeButtonActivity
import vn.loitp.a.cv.bt.circularImageClick.CircularImageClickActivity
import vn.loitp.a.cv.bt.circularProgress.CPBActivity
import vn.loitp.app.a.cv.bt.fab.FabActivity
import vn.loitp.app.a.cv.bt.fit.FitButtonActivity
import vn.loitp.app.a.cv.bt.goodView.GoodViewActivity
import vn.loitp.app.a.cv.bt.l.LButtonActivity
import vn.loitp.app.a.cv.bt.q.QButtonActivity
import vn.loitp.app.a.cv.bt.shine.ShineButtonActivity

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

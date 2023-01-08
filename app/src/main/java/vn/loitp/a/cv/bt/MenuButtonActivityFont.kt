package vn.loitp.a.cv.bt

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.activity_menu_button.*
import vn.loitp.R
import vn.loitp.a.cv.bt.autoSize.AutoSizeButtonActivityFont
import vn.loitp.a.cv.bt.circularImageClick.CircularImageClickActivityFont
import vn.loitp.a.cv.bt.circularProgress.CPBActivityFont
import vn.loitp.a.cv.bt.fab.FabActivityFont
import vn.loitp.a.cv.bt.fit.FitButtonActivityFont
import vn.loitp.a.cv.bt.goodView.GoodViewActivityFont
import vn.loitp.a.cv.bt.l.LButtonActivityFont
import vn.loitp.a.cv.bt.q.QButtonActivityFont
import vn.loitp.a.cv.bt.shine.ShineButtonActivityFont

@LogTag("MenuButtonActivity")
@IsFullScreen(false)
class MenuButtonActivityFont : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuButtonActivityFont::class.java.simpleName
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
            btShineButton -> launchActivity(ShineButtonActivityFont::class.java)
            btCircularImageClick -> launchActivity(CircularImageClickActivityFont::class.java)
            btGoodView -> launchActivity(GoodViewActivityFont::class.java)
            btlButton -> launchActivity(LButtonActivityFont::class.java)
            btAutoSizeButton -> launchActivity(AutoSizeButtonActivityFont::class.java)
            btQButton -> launchActivity(QButtonActivityFont::class.java)
            btFab -> launchActivity(FabActivityFont::class.java)
            btFitButtonActivity -> launchActivity(FitButtonActivityFont::class.java)
            btCircularProgressButton -> launchActivity(CPBActivityFont::class.java)
        }
    }
}

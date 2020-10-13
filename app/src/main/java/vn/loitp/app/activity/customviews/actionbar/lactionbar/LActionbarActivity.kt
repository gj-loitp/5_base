package vn.loitp.app.activity.customviews.actionbar.lactionbar

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.LToast
import com.views.actionbar.LActionBar
import kotlinx.android.synthetic.main.activity_l_action_bar.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_l_action_bar)
@LogTag("LActionbarActivity")
@IsFullScreen(false)
class LActionbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupActionBar()
    }

    private fun setupActionBar() {
        textView.text = LStoreUtil.readTxtFromRawFolder(nameOfRawFile = R.raw.lactionbar)

        lActionBar.setOnClickBack(object : LActionBar.Callback {
            override fun onClickBack(view: View) {
                onBackPressed()
            }

            override fun onClickMenu(view: View) {
                LToast.show("onClickMenu")
            }
        })
        lActionBar.showMenuIcon()
        lActionBar.showShadowView()
        lActionBar.setImageMenuIcon(R.mipmap.ic_launcher)
        lActionBar.setTvTitle("Demo LActionbarActivity")
    }
}

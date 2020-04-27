package vn.loitp.app.activity.customviews.actionbar.lactionbar

import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.LToast
import com.views.actionbar.LActionBar
import kotlinx.android.synthetic.main.activity_l_action_bar.*
import vn.loitp.app.R

class LActionbarActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_l_action_bar
    }

    private fun setupActionBar() {
        tv.text = LStoreUtil.readTxtFromRawFolder(activity, R.raw.lactionbar)

        lActionBar.setOnClickBack(object : LActionBar.Callback {
            override fun onClickBack(view: View) {
                onBackPressed()
            }

            override fun onClickMenu(view: View) {
                LToast.show(activity, "onClickMenu")
            }
        })
        lActionBar.showMenuIcon()
        lActionBar.showShadowView()
        lActionBar.setImageMenuIcon(R.mipmap.ic_launcher)
        lActionBar.setTvTitle("Demo LActionbarActivity")
    }
}

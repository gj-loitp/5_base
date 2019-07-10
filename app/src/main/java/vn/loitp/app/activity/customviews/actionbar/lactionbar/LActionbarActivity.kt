package vn.loitp.app.activity.customviews.actionbar.lactionbar

import android.os.Bundle
import android.widget.TextView
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import loitp.basemaster.R
import vn.loitp.views.LToast
import vn.loitp.views.actionbar.LActionBar

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
        return R.layout.activity_action_bar
    }

    private fun setupActionBar() {
        val lActionBar = findViewById<LActionBar>(R.id.l_action_bar)
        val tv = findViewById<TextView>(R.id.tv)
        tv.text = LStoreUtil.readTxtFromRawFolder(activity, R.raw.lactionbar)

        lActionBar.setOnClickBack(object : LActionBar.Callback {
            override fun onClickBack() {
                onBackPressed()
            }

            override fun onClickMenu() {
                LToast.show(activity, "onClickMenu")
            }
        })
        lActionBar.showMenuIcon()
        lActionBar.setImageMenuIcon(R.mipmap.ic_launcher)
        lActionBar.setTvTitle("Demo LActionbarActivity")
    }
}

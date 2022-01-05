package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.autoselect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tablayout.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.RvTabDemoBasicActivity

class RvTabAutoSelectActivityRvTab : RvTabDemoBasicActivity() {

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabAutoSelectActivityRvTab::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerTabLayout.setAutoSelectionMode(true)
    }
}

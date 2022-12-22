package vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.autoSelect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_recycler_tab_layout.*
import vn.loitp.R
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.Demo
import vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout.basic.RvTabDemoBasicActivity

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
        return R.layout.activity_recycler_tab_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        recyclerTabLayout.setAutoSelectionMode(true)
    }
}

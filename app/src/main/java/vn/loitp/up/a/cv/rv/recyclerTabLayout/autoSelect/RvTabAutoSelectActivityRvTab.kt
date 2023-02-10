package vn.loitp.up.a.cv.rv.recyclerTabLayout.autoSelect

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_recycler_tab_layout.*
import vn.loitp.R
import vn.loitp.up.a.cv.rv.recyclerTabLayout.Demo
import vn.loitp.up.a.cv.rv.recyclerTabLayout.basic.RvTabDemoBasicActivity

class RvTabAutoSelectActivityRvTab : RvTabDemoBasicActivity() {

    companion object {

        fun startActivity(context: Context, demo: Demo) {
            val intent = Intent(context, RvTabAutoSelectActivityRvTab::class.java)
            intent.putExtra(KEY_DEMO, demo.name)
            context.startActivity(intent)
            context.tranIn()
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_recycler_tab_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        recyclerTabLayout.setAutoSelectionMode(true)
    }
}

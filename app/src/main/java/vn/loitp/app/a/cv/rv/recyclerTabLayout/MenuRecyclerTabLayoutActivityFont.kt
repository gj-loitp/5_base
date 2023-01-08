package vn.loitp.app.a.cv.rv.recyclerTabLayout

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.activity_menu_recycler_tablayout.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.recyclerTabLayout.autoSelect.RvTabAutoSelectActivityRvTabFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.basic.RvTabDemoBasicActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.customView01.RvTabCustomView01ActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.customView02.RvTabCustomView02ActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.imitationLoop.RvTabImitationLoopActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.rtl.RvTabDemoRtlActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.tabOnScreenLimit.RvTabOnScreenLimitActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.tabScrollDisabled.RvTabScrollDisabledActivityFont
import vn.loitp.app.a.cv.rv.recyclerTabLayout.years.RvTabYearsActivityFont

@LogTag("MenuRecyclerTabLayoutActivity")
@IsFullScreen(false)
class MenuRecyclerTabLayoutActivityFont : BaseActivityFont(), AdapterView.OnItemClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_recycler_tablayout
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
            this.tvTitle?.text = MenuRecyclerTabLayoutActivityFont::class.java.simpleName
        }
        listView.onItemClickListener = this
        val adapter = ArrayAdapter<String>(this, R.layout.i_test_retrofit)

        for (demo in Demo.values()) {
            adapter.add(getString(demo.titleResId))
        }

        listView.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (val demo = Demo.values()[position]) {
            Demo.BASIC -> RvTabDemoBasicActivityFont.startActivity(this, demo)
            Demo.AUTO_SELECT -> RvTabAutoSelectActivityRvTabFont.startActivity(this, demo)
            Demo.CUSTOM_VIEW01 -> RvTabCustomView01ActivityFont.startActivity(this, demo)
            Demo.CUSTOM_VIEW02 -> RvTabCustomView02ActivityFont.startActivity(this, demo)
            Demo.YEARS -> RvTabYearsActivityFont.startActivity(this, demo)
            Demo.IMITATION_LOOP -> RvTabImitationLoopActivityFont.startActivity(this, demo)
            Demo.RTL -> RvTabDemoRtlActivityFont.startActivity(this, demo)
            Demo.TAB_SCROLL_DISABLED -> RvTabScrollDisabledActivityFont.startActivity(this, demo)
            Demo.TAB_ON_SCREEN_LIMIT -> RvTabOnScreenLimitActivityFont.startActivity(this, demo)
        }
    }
}

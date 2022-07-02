package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_recycler_tablayout_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.autoselect.RvTabAutoSelectActivityRvTab
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.RvTabDemoBasicActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01.RvTabCustomView01Activity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02.RvTabCustomView02Activity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop.RvTabImitationLoopActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.rtl.RvTabDemoRtlActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabonscreenlimit.RvTabOnScreenLimitActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabscrolldisabled.RvTabScrollDisabledActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years.RvTabYearsActivity

@LogTag("RecyclerTabLayoutMenuActivity")
@IsFullScreen(false)
class RecyclerTabLayoutMenuActivity : BaseFontActivity(), AdapterView.OnItemClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        listView.onItemClickListener = this
//        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val adapter = ArrayAdapter<String>(this, R.layout.view_item_test_retrofit)

        for (demo in Demo.values()) {
            adapter.add(getString(demo.titleResId))
        }

        listView.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (val demo = Demo.values()[position]) {
            Demo.BASIC -> RvTabDemoBasicActivity.startActivity(this, demo)
            Demo.AUTO_SELECT -> RvTabAutoSelectActivityRvTab.startActivity(this, demo)
            Demo.CUSTOM_VIEW01 -> RvTabCustomView01Activity.startActivity(this, demo)
            Demo.CUSTOM_VIEW02 -> RvTabCustomView02Activity.startActivity(this, demo)
            Demo.YEARS -> RvTabYearsActivity.startActivity(this, demo)
            Demo.IMITATION_LOOP -> RvTabImitationLoopActivity.startActivity(this, demo)
            Demo.RTL -> RvTabDemoRtlActivity.startActivity(this, demo)
            Demo.TAB_SCROLL_DISABLED -> RvTabScrollDisabledActivity.startActivity(this, demo)
            Demo.TAB_ON_SCREEN_LIMIT -> RvTabOnScreenLimitActivity.startActivity(this, demo)
        }
    }
}

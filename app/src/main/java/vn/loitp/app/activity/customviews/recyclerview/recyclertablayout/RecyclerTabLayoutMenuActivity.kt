package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import com.core.base.BaseFontActivity

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.autoselect.DemoAutoSelectActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.basic.DemoBasicActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview01.DemoCustomView01Activity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02.DemoCustomView02Activity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.imitationloop.DemoImitationLoopActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.rtl.DemoRtlActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabonscreenlimit.DemoTabOnScreenLimitActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.tabscrolldisabled.DemoTabScrollDisabledActivity
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.years.DemoYearsActivity

class RecyclerTabLayoutMenuActivity : BaseFontActivity(), AdapterView.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listView = findViewById<ListView>(R.id.list)
        listView.onItemClickListener = this
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        for (demo in Demo.values()) {
            adapter.add(getString(demo.titleResId))
        }

        listView.adapter = adapter
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val demo = Demo.values()[position]
        when (demo) {
            Demo.BASIC -> DemoBasicActivity.startActivity(this, demo)
            Demo.AUTO_SELECT -> DemoAutoSelectActivity.startActivity(this, demo)
            Demo.CUSTOM_VIEW01 -> DemoCustomView01Activity.startActivity(this, demo)
            Demo.CUSTOM_VIEW02 -> DemoCustomView02Activity.startActivity(this, demo)
            Demo.YEARS -> DemoYearsActivity.startActivity(this, demo)
            Demo.IMITATION_LOOP -> DemoImitationLoopActivity.startActivity(this, demo)
            Demo.RTL -> DemoRtlActivity.startActivity(this, demo)
            Demo.TAB_SCROLL_DISABLED -> DemoTabScrollDisabledActivity.startActivity(this, demo)
            Demo.TAB_ON_SCREEN_LIMIT -> DemoTabOnScreenLimitActivity.startActivity(this, demo)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_tablayout_menu
    }
}

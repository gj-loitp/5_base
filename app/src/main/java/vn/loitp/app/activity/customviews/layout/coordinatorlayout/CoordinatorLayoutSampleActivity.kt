package vn.loitp.app.activity.customviews.layout.coordinatorlayout

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil

import vn.loitp.app.R

//http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
class CoordinatorLayoutSampleActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent.getStringExtra(KEY)) {
            VALUE_0 -> LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.fl_container, fragment = FrmCoordinator0(), isAddToBackStack = false)
            VALUE_1 -> LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.fl_container, fragment = FrmCoordinator1(), isAddToBackStack = false)
            VALUE_2 -> LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.fl_container, fragment = FrmCoordinator2(), isAddToBackStack = false)
            VALUE_3 -> LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.fl_container, fragment = FrmCoordinator3(), isAddToBackStack = false)
            VALUE_4 -> LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.fl_container, fragment = FrmCoordinator4(), isAddToBackStack = false)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_coordinator_layout_sample
    }

    companion object {
        const val KEY = "KEY"
        const val VALUE_0 = "Standard App bar scrolling with only Toolbar"
        const val VALUE_1 = "App bar scrolling with tabs"
        const val VALUE_2 = "App bar scrolling with Flexible space"
        const val VALUE_3 = "App bar scrolling with overlapping content in Flexible space"
        const val VALUE_4 = "Recyclerview"
    }

}

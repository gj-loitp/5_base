package vn.loitp.app.activity.customviews.layout.swiperefreshlayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withrecyclerview.SwipeRefreshLayoutRecyclerViewActivity
import vn.loitp.app.activity.customviews.layout.swiperefreshlayout.withscrollview.SwipeRefreshLayoutScrollViewActivity

@LayoutId(R.layout.activity_swipe_refresh_menu_layout)
class SwipeRefreshLayoutMenuActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_with_scroll_view).setOnClickListener { _ ->
            val intent = Intent(activity, SwipeRefreshLayoutScrollViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt_with_recycler_view).setOnClickListener { _ ->
            val intent = Intent(activity, SwipeRefreshLayoutRecyclerViewActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}

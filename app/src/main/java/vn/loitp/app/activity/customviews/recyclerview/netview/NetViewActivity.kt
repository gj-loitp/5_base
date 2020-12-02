package vn.loitp.app.activity.customviews.recyclerview.netview

import android.os.Bundle
import android.view.MotionEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_net_view.*
import vn.loitp.app.R


@LogTag("NetViewActivity")
@IsFullScreen(false)
class NetViewActivity : BaseFontActivity() {

    private var netAdapter = NetAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_net_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        netAdapter.onClickRootView = { net ->
            showShortInformation(net.name)
        }
        rvNetView.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Stop only scrolling.
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        rvNetView.layoutManager = GridLayoutManager(this, 1)
        rvNetView.adapter = netAdapter

        btAddItem.setOnClickListener {
            addNet()
        }
        btClearAll.setOnClickListener {
            netAdapter.clear()
        }
    }

    private fun addNet() {
        val net = Net()
        net.name = "${netAdapter.itemCount + 1}"
        netAdapter.addNet(net)

        when (netAdapter.itemCount) {
            0 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            1 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            2 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            3 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            4 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            5 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            6 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            7 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            8 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            9 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            10 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            else -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
        }
    }

}

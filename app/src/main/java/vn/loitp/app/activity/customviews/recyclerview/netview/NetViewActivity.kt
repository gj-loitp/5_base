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
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            8 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            9 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            10 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            11 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            12 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            13 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            14 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            15 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            16 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            17 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            18 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            19 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            20 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            21 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            22 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            23 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            24 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            25 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            26 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            27 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            28 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            29 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            30 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            else -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
        }
    }

}

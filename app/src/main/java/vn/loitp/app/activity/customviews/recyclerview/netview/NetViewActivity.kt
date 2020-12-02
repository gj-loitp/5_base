package vn.loitp.app.activity.customviews.recyclerview.netview

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_net_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants
import java.util.*
import kotlin.collections.ArrayList

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

        rvNetView.layoutManager = GridLayoutManager(this, 1)
        rvNetView.adapter = netAdapter

        btAddItem.setOnClickListener {
            addNet()
        }
    }

    private fun addNet() {
        val net = Net()
        net.name = "${netAdapter.itemCount + 1}"
        netAdapter.addNet(net)

        when (netAdapter.itemCount) {
            0, 1 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            2, 3, 4, 5, 6 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            7, 8, 9 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            else -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
        }
    }

}

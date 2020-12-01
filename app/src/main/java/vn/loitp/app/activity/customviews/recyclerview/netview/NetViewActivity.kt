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

    private val listNet = ArrayList<Net>()
    private var netAdapter = NetAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_net_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        rvNetView.layoutManager = GridLayoutManager(this, 3)
        rvNetView.adapter = netAdapter
    }

    private fun setupData() {
        for (i in 0..10) {
            val net = Net()
            net.name = "$i"
            listNet.add(net)
        }
        netAdapter.setListNet(listNet)
    }

}

package vn.loitp.app.activity.customviews.recyclerview.mergeadapter

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_recyclerview_merge_adapter.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.BannerAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.MyDetailAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.UsersAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.DataSource
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.MyDetail

class MergeAdapterActivity : BaseFontActivity() {
    lateinit var adapter: MergeAdapter
    lateinit var myDetailAdapter: MyDetailAdapter
    lateinit var userVerticalAdapter: UsersAdapter
    lateinit var bannerAdapter: BannerAdapter
    val myDetail = MyDetail(1, "Himanshu Singh", "I am an writer and Open Source contributor in MindOrks.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataInRecyclerView()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recyclerview_merge_adapter
    }

    private fun setupDataInRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        userVerticalAdapter = UsersAdapter(DataSource.getUser())
        bannerAdapter = BannerAdapter(DataSource.getBanner())
        myDetailAdapter = MyDetailAdapter(myDetail)
        val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(myDetailAdapter, userVerticalAdapter, bannerAdapter)
        adapter = MergeAdapter(listOfAdapters)
        recyclerView.adapter = adapter

    }
}

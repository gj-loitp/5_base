package vn.loitp.app.activity.demo.rss

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LSocialUtil
import com.rss.RssConverterFactory
import com.rss.RssFeed
import com.rss.RssItem
import kotlinx.android.synthetic.main.fragment_rss.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import vn.loitp.app.R

/**
 * Fragment for listing fetched [RssItem] list
 */
@LogTag("RssFragment")
class RssFragment : BaseFragment(),
        SwipeRefreshLayout.OnRefreshListener,
        RssItemsAdapter.OnItemClickListener {

    companion object {
        private const val KEY_FEED = "FEED"

        fun newInstance(feedUrl: String): RssFragment {
            val rssFragment = RssFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_FEED, feedUrl)
            rssFragment.arguments = bundle
            return rssFragment
        }
    }

    private var feedUrl: String? = null
    private lateinit var mAdapter: RssItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        feedUrl = arguments?.getString(KEY_FEED)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.fragment_rss
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = RssItemsAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = mAdapter
        swRefresh.setOnRefreshListener(this)

        fetchRss()
    }

    private fun fetchRss() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://github.com")
                .addConverterFactory(RssConverterFactory.create())
                .build()

        showLoading()
        val service = retrofit.create(RssService::class.java)

        feedUrl?.apply {
            service.getRss(this)
                    .enqueue(object : Callback<RssFeed> {
                        override fun onResponse(call: Call<RssFeed>, response: Response<RssFeed>) {
                            response.body()?.items?.let {
                                onRssItemsLoaded(rssItems = it)
                            }
                            hideLoading()
                        }

                        override fun onFailure(call: Call<RssFeed>, t: Throwable) {
                            showSnackBarError(msg = "Failed to fetchRss RSS feed!", isFullWidth = true)
                        }
                    })
        }
    }

    fun onRssItemsLoaded(rssItems: List<RssItem>) {
        mAdapter.setItems(rssItems)
        mAdapter.notifyDataSetChanged()
        if (recyclerView.visibility != View.VISIBLE) {
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        swRefresh.isRefreshing = true
    }

    fun hideLoading() {
        swRefresh.isRefreshing = false
    }

    override fun onRefresh() {
        fetchRss()
    }

    override fun onItemSelected(rssItem: RssItem) {
        LSocialUtil.openUrlInBrowser(context = context, url = rssItem.link)
    }

}

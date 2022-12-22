package vn.loitp.app.activity.demo.rss

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.core.utilities.LSocialUtil
import com.loitp.rss.RssConverterFactory
import com.loitp.rss.RssFeed
import com.loitp.rss.RssItem
import kotlinx.android.synthetic.main.frm_rss.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import vn.loitp.R

@LogTag("RssFragment")
class RssFragment :
    BaseFragment(),
    SwipeRefreshLayout.OnRefreshListener {

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
        return R.layout.frm_rss
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        mAdapter = RssItemsAdapter { rssItem ->
            LSocialUtil.openUrlInBrowser(context = context, url = rssItem.link)
        }
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
}

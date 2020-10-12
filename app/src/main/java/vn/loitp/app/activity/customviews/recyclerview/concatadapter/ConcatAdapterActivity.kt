package vn.loitp.app.activity.customviews.recyclerview.concatadapter

import android.os.Bundle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LUIUtil
import com.interfaces.CallbackRecyclerView
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_recycler_view_concat_adapter.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.adapter.*
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.data.DataSource
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.data.model.AboutMe
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.data.model.News

//https://blog.mindorks.com/implementing-merge-adapter-in-android-tutorial

@LayoutId(R.layout.activity_recycler_view_concat_adapter)
@LogTag("MergeAdapterActivity")
@IsFullScreen(false)
class ConcatAdapterActivity : BaseFontActivity() {
    private var concatAdapter: ConcatAdapter? = null
    private var aboutMeAdapter: AboutMeAdapter? = null
    private var usersAdapter: UsersAdapter? = null
    private var bannerAdapter: BannerAdapter? = null
    private var newsAdapter: NewsAdapter? = null
    private val loadingAdapter = LoadingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataInRecyclerView()
    }

    private fun setupDataInRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        aboutMeAdapter = AboutMeAdapter(ArrayList())
        usersAdapter = UsersAdapter(ArrayList())
        bannerAdapter = BannerAdapter(ArrayList())
        newsAdapter = NewsAdapter(ArrayList())

        aboutMeAdapter?.let { ama ->
            ama.onClickRootListener = { aboutMe, position ->
                aboutMe.name = System.currentTimeMillis().toString()
                ama.notifyItemChanged(position)
            }
        }

        usersAdapter?.let { ua ->
            ua.onClickRootListener = { user, position ->
                user.avatar = Constants.URL_IMG_1
                user.name = System.currentTimeMillis().toString()
                ua.notifyItemChanged(position)
            }
        }

        newsAdapter?.let { na ->
            na.onClickRootListener = { _, position ->
                showShort("Click position $position")
            }
        }

        aboutMeAdapter?.let { ama ->
            usersAdapter?.let { ua ->
                bannerAdapter?.let { ba ->
                    newsAdapter?.let { na ->
                        val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(ama, ua, ba, na)
                        concatAdapter = ConcatAdapter(listOfAdapters)
                    }
                }
            }
        }

        recyclerView.adapter = concatAdapter

        LUIUtil.setScrollChange(recyclerView, object : CallbackRecyclerView {
            override fun onTop() {
                logD("onTop")
            }

            override fun onBottom() {
                logD("onBottom")
                showShort("onBottom")
                genNewsData()
            }
        })

        btClearAll.setSafeOnClickListener {
            concatAdapter?.let { a ->
                a.adapters.let { list ->
                    list.forEach { childAdapter ->
                        a.removeAdapter(childAdapter)
                    }
                }
            }
        }
        btGenAboutMe.setSafeOnClickListener {
            val aboutMe = AboutMe(1, "Loitp93", "I'm a developer.")
            val listAboutMe = ArrayList<AboutMe>()
            listAboutMe.add(aboutMe)
            aboutMeAdapter?.setData(listAboutMe)
        }
        btGenListUser.setSafeOnClickListener {
            usersAdapter?.setData(DataSource.getListUser())
        }
        btGenBanner.setSafeOnClickListener {
            bannerAdapter?.setData(DataSource.getBanner())
        }
        btAddBannerAt0.setSafeOnClickListener {
            val newBannerAdapter = BannerAdapter(ArrayList())
            newBannerAdapter.setData(DataSource.getBanner())
            concatAdapter?.addAdapter(0, newBannerAdapter)
        }
        btAddAboutMeAtLast.setSafeOnClickListener {
            val newAboutMeAdapter = AboutMeAdapter(ArrayList())
            val aboutMe = AboutMe(1, "Loitp ^^!", "Hello world!!!")
            val listAboutMe = ArrayList<AboutMe>()
            listAboutMe.add(aboutMe)
            newAboutMeAdapter.setData(listAboutMe)
            concatAdapter?.addAdapter(newAboutMeAdapter)
        }
        btRemoveAdapterListUser.setSafeOnClickListener {
            usersAdapter?.let { ua ->
                concatAdapter?.removeAdapter(ua)
            }
        }

        genNewsData()
    }

    private fun isLoading(): Boolean {
        concatAdapter?.let {
            it.adapters.forEach { childAdapter ->
                if (childAdapter == loadingAdapter) {
                    return true
                }
            }
        }
        return false
    }

    private fun genNewsData() {
        if (!isLoading()) {
            concatAdapter?.addAdapter(loadingAdapter)
            concatAdapter?.itemCount?.let {
                recyclerView.scrollToPosition(it - 1)
            }

            LUIUtil.setDelay(2000, Runnable {
                val listNews = ArrayList<News>()
                for (i in 0..10) {
                    val news = News(
                            id = System.currentTimeMillis(),
                            title = "Title " + System.currentTimeMillis(),
                            image = Constants.URL_IMG_10
                    )
                    listNews.add(news)
                }
                concatAdapter?.removeAdapter(loadingAdapter)
                newsAdapter?.addData(listNews)
            })
        }
    }
}

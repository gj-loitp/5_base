package vn.loitp.app.activity.customviews.recyclerview.mergeadapter

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LUIUtil
import com.interfaces.RecyclerViewCallback
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_recyclerview_merge_adapter.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.*
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.DataSource
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.AboutMe
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.News

//https://blog.mindorks.com/implementing-merge-adapter-in-android-tutorial
class MergeAdapterActivity : BaseFontActivity() {
    private var mergeAdapter: MergeAdapter? = null
    private var aboutMeAdapter: AboutMeAdapter? = null
    private var usersAdapter: UsersAdapter? = null
    private var bannerAdapter: BannerAdapter? = null
    private var newsAdapter: NewsAdapter? = null
    private val loadingAdapter = LoadingAdapter()

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
                        mergeAdapter = MergeAdapter(listOfAdapters)
                    }
                }
            }
        }

        recyclerView.adapter = mergeAdapter

        LUIUtil.setScrollChange(recyclerView, object : RecyclerViewCallback {
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
            mergeAdapter?.let { a ->
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
            mergeAdapter?.addAdapter(0, newBannerAdapter)
        }
        btAddAboutMeAtLast.setSafeOnClickListener {
            val newAboutMeAdapter = AboutMeAdapter(ArrayList())
            val aboutMe = AboutMe(1, "Loitp ^^!", "Hello world!!!")
            val listAboutMe = ArrayList<AboutMe>()
            listAboutMe.add(aboutMe)
            newAboutMeAdapter.setData(listAboutMe)
            mergeAdapter?.addAdapter(newAboutMeAdapter)
        }
        btRemoveAdapterListUser.setSafeOnClickListener {
            usersAdapter?.let { ua ->
                mergeAdapter?.removeAdapter(ua)
            }
        }

        genNewsData()
    }

    private fun isLoading(): Boolean {
        mergeAdapter?.let {
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
            mergeAdapter?.addAdapter(loadingAdapter)
            mergeAdapter?.itemCount?.let {
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
                mergeAdapter?.removeAdapter(loadingAdapter)
                newsAdapter?.addData(listNews)
            })
        }
    }
}

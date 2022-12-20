package vn.loitp.app.activity.customviews.recyclerview.concatAdapter

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_concat_adapter.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.concatAdapter.adapter.*
import vn.loitp.app.activity.customviews.recyclerview.concatAdapter.data.DataSource
import vn.loitp.app.activity.customviews.recyclerview.concatAdapter.data.model.AboutMe
import vn.loitp.app.activity.customviews.recyclerview.concatAdapter.data.model.News

@LogTag("MergeAdapterActivity")
@IsFullScreen(false)
class ConcatAdapterActivity : BaseFontActivity() {
    private var concatAdapter: ConcatAdapter? = null
    private var aboutMeAdapter: AboutMeAdapter? = null
    private var usersAdapter: UsersAdapter? = null
    private var bannerAdapter: BannerAdapter? = null
    private var newsAdapter: NewsAdapter? = null
    private val loadingAdapter = LoadingAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_concat_adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://blog.mindorks.com/implementing-merge-adapter-in-android-tutorial"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ConcatAdapterActivity::class.java.simpleName
        }

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
                showShortInformation("Click position $position")
            }
        }

        aboutMeAdapter?.let { ama ->
            usersAdapter?.let { ua ->
                bannerAdapter?.let { ba ->
                    newsAdapter?.let { na ->
                        val listOfAdapters =
                            listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(
                                ama,
                                ua,
                                ba,
                                na
                            )
                        concatAdapter = ConcatAdapter(listOfAdapters)
                    }
                }
            }
        }

        recyclerView.adapter = concatAdapter

        LUIUtil.setScrollChange(
            recyclerView = recyclerView,
            onTop = {
                logD("onTop")
            },
            onBottom = {
                logD("onBottom")
                showShortInformation("onBottom")
                genNewsData()
            }
        )

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

            LUIUtil.setDelay(mls = 2000) {
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
            }
        }
    }
}

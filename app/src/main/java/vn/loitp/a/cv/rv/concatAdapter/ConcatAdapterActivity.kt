package vn.loitp.a.cv.rv.concatAdapter

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.common.URL_IMG_10
import com.loitp.core.ext.*
import kotlinx.android.synthetic.main.a_rv_concat_adapter.*
import vn.loitp.R
import vn.loitp.a.cv.rv.concatAdapter.adt.*
import vn.loitp.a.cv.rv.concatAdapter.data.DataSource
import vn.loitp.a.cv.rv.concatAdapter.data.model.AboutMe
import vn.loitp.a.cv.rv.concatAdapter.data.model.News

@LogTag("MergeAdapterActivity")
@IsFullScreen(false)
class ConcatAdapterActivity : BaseActivityFont() {
    private var concatAdapter: ConcatAdapter? = null
    private var aboutMeAdapter: AboutMeAdapter? = null
    private var usersAdapter: UsersAdapter? = null
    private var bannerAdapter: BannerAdapter? = null
    private var newsAdapter: NewsAdapter? = null
    private val loadingAdapter = LoadingAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_concat_adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
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
                user.avatar = URL_IMG_1
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

        recyclerView.setScrollChange(
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
                showShortInformation("removeAdapter success")
                btGenAboutMe.isVisible = false
                btGenListUser.isVisible = false
                btGenBanner.isVisible = false
            }
        }
        btGenAboutMe.setSafeOnClickListener {
            val aboutMe = AboutMe(1, "Loitp93", "I'm a developer ${System.currentTimeMillis()}")
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

            setDelay(mls = 2000) {
                val listNews = ArrayList<News>()
                for (i in 0..10) {
                    val news = News(
                        id = System.currentTimeMillis(),
                        title = "Title " + System.currentTimeMillis(),
                        image = URL_IMG_10
                    )
                    listNews.add(news)
                }
                concatAdapter?.removeAdapter(loadingAdapter)
                newsAdapter?.addData(listNews)
            }
        }
    }
}

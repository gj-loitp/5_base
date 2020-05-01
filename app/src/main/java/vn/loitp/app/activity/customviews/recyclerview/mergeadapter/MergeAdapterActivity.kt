package vn.loitp.app.activity.customviews.recyclerview.mergeadapter

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import androidx.recyclerview.widget.RecyclerView
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.interfaces.RecyclerViewCallback
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_recyclerview_merge_adapter.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.AboutMeAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.BannerAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter.UsersAdapter
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.DataSource
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.AboutMe

//https://blog.mindorks.com/implementing-merge-adapter-in-android-tutorial
class MergeAdapterActivity : BaseFontActivity() {
    private var mergeAdapter: MergeAdapter? = null
    private var aboutMeAdapter: AboutMeAdapter? = null
    private var usersAdapter: UsersAdapter? = null
    private var bannerAdapter: BannerAdapter? = null

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

        aboutMeAdapter?.let { ama ->
            usersAdapter?.let { ua ->
                bannerAdapter?.let { ba ->
                    val listOfAdapters = listOf<RecyclerView.Adapter<out RecyclerView.ViewHolder>>(ama, ua, ba)
                    mergeAdapter = MergeAdapter(listOfAdapters)
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
            }
        })

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
    }
}

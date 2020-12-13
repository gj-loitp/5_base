package vn.loitp.app.activity.customviews.wwlvideo

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.views.wwlmusic.utils.LWWLMusicUiUtil
import kotlinx.android.synthetic.main.wwl_video_home_fragment.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset
import java.util.*

@LogTag("HomeFragment")
class HomeFragment : BaseFragment() {

    private var gridLayoutManager: GridLayoutManager? = null
    private var customAdapter: CustomAdapter? = null
    private var fragmentHost: FragmentHost? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_video_home_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayoutManager = GridLayoutManager(activity, LWWLMusicUiUtil.getGridColumnCount(resources))
        recyclerView.layoutManager = gridLayoutManager
        customAdapter = CustomAdapter(WWLVideoDataset.datasetItems)
        recyclerView.adapter = customAdapter
        updateLayoutIfNeed()
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        fragmentHost = activity as FragmentHost
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateLayoutIfNeed()
    }

    private fun updateLayoutIfNeed() {
        if (gridLayoutManager != null) {
            gridLayoutManager!!.spanCount = LWWLMusicUiUtil.getGridColumnCount(resources)
        }
        if (customAdapter != null) {
            customAdapter!!.notifyDataSetChanged()
        }
    }

    private fun onItemClicked(item: WWLVideoDataset.DatasetItem) {
        if (fragmentHost != null) {
            fragmentHost!!.goToDetail(item)
        }
    }

    private inner class CustomAdapter(private val mDataSet: ArrayList<WWLVideoDataset.DatasetItem>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.wwl_video_card_row_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.titleView.text = mDataSet[position].title
            holder.subTitleView.text = mDataSet[position].subtitle
        }

        override fun getItemCount(): Int {
            return mDataSet.size
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val titleView: TextView
            val subTitleView: TextView

            init {
                v.setOnClickListener { v1: View? -> onItemClicked(mDataSet[bindingAdapterPosition]) }
                titleView = v.findViewById(R.id.liTitle)
                subTitleView = v.findViewById(R.id.liSubtitle)
            }
        }
    }
}
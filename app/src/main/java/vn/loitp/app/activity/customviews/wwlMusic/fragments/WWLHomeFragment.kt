package vn.loitp.app.activity.customviews.wwlMusic.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil.getGridColumnCount
import kotlinx.android.synthetic.main.wwl_music_home_fragment.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlMusic.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlMusic.utils.WWLMusicDataset

@LogTag("WWLHomeFragment")
class WWLHomeFragment : BaseFragment() {

    private var mLayoutManager: GridLayoutManager? = null
    private var customAdapter: CustomAdapter? = null
    private var mFragmentHost: FragmentHost? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_music_home_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        updateLayoutIfNeed()
    }

    private fun setupViews() {
        mLayoutManager = GridLayoutManager(activity, getGridColumnCount(resources))
        recyclerView.layoutManager = mLayoutManager
        customAdapter = CustomAdapter(WWLMusicDataset.datasetItems)
        recyclerView.adapter = customAdapter
    }

    @Deprecated("Deprecated in Java")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mFragmentHost = activity as FragmentHost
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateLayoutIfNeed()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLayoutIfNeed() {
        mLayoutManager?.spanCount = getGridColumnCount(resources)
        customAdapter?.notifyDataSetChanged()
    }

    private fun onItemClicked(item: WWLMusicDataset.DatasetItem) {
        mFragmentHost?.goToDetail(item)
    }

    private inner class CustomAdapter(private val mDataSet: ArrayList<WWLMusicDataset.DatasetItem>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.wwl_music_card_row_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.liTitle.text = mDataSet[position].title
            holder.liSubtitle.text = mDataSet[position].subtitle
        }

        override fun getItemCount(): Int {
            return mDataSet.size
        }

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val liTitle: TextView
            val liSubtitle: TextView

            init {
                v.setOnClickListener {
                    onItemClicked(mDataSet[bindingAdapterPosition])
                }
                liTitle = v.findViewById(R.id.liTitle)
                liSubtitle = v.findViewById(R.id.liSubtitle)
            }
        }
    }
}

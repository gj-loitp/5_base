package vn.loitp.app.activity.customviews.wwlmusic.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.base.BaseFragment
import kotlinx.android.synthetic.main.wwl_music_playlist_fragment.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlmusic.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlmusic.utils.WWLMusicDataset
import java.util.* // ktlint-disable no-wildcard-imports

@LogTag("WWLPlaylistFragment")
class WWLPlaylistFragment : BaseFragment() {
    private var mLayoutManager: LinearLayoutManager? = null
    private var mAdapter: CustomAdapter? = null
    private var mFragmentHost: FragmentHost? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_music_playlist_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.scrollToPosition(0)
        mAdapter = CustomAdapter(WWLMusicDataset.datasetItems)
        recyclerView.adapter = mAdapter
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mFragmentHost = activity as FragmentHost
    }

    private fun onItemClicked(item: WWLMusicDataset.DatasetItem) {
        mFragmentHost?.goToDetail(item)
    }

    fun updateItem(item: WWLMusicDataset.DatasetItem) {
        liTitle.text = item.title
        liSubtitle.text = item.subtitle
    }

    private inner class CustomAdapter(private val mDataSet: ArrayList<WWLMusicDataset.DatasetItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.wwl_music_playlist_item, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is ViewHolder) {
                holder.liTitle.text = mDataSet[position].title
                holder.liSubtitle.text = mDataSet[position].subtitle
            }
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

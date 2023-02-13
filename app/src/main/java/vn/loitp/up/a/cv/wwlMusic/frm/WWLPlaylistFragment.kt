package vn.loitp.up.a.cv.wwlMusic.frm

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import kotlinx.android.synthetic.main.f_wwl_music_playlist.*
import vn.loitp.R
import vn.loitp.up.a.cv.wwlMusic.itf.FragmentHost
import vn.loitp.up.a.cv.wwlMusic.utils.WWLMusicDataset

@LogTag("WWLPlaylistFragment")
class WWLPlaylistFragment : BaseFragment() {
    private var mLayoutManager: LinearLayoutManager? = null
    private var mAdapter: CustomAdapter? = null
    private var mFragmentHost: FragmentHost? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.f_wwl_music_playlist
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        mLayoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.scrollToPosition(0)
        mAdapter = CustomAdapter(WWLMusicDataset.datasetItems)
        recyclerView.adapter = mAdapter
    }

    @Deprecated("Deprecated in Java")
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

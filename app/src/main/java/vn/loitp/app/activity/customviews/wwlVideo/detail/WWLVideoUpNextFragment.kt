package vn.loitp.app.activity.customviews.wwlVideo.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFragment
import com.loitp.views.wwl.music.utils.LWWLMusicUiUtil
import kotlinx.android.synthetic.main.wwl_video_up_next_fragment.*
import vn.loitp.R
import vn.loitp.app.activity.customviews.wwlVideo.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlVideo.utils.WWLVideoDataset

@LogTag("WWLVideoUpNextFragment")
class WWLVideoUpNextFragment : BaseFragment() {

    companion object {
        private const val HEADER = 0
        private const val TITLE = 1
        private const val OTHER = 2
    }

    private var mFragmentHost: FragmentHost? = null
    private var mLayoutManager: GridLayoutManager? = null
    private var mAdapter: CustomAdapter? = null
    private var mIsLandscape = false

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_video_up_next_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        updateLayoutIfNeed()
    }

    private fun setupViews() {
        mLayoutManager = GridLayoutManager(activity, LWWLMusicUiUtil.getGridColumnCount(resources))
        recyclerView.layoutManager = mLayoutManager
        mAdapter = CustomAdapter(WWLVideoDataset.datasetItems)
        recyclerView.adapter = mAdapter
        mLayoutManager?.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return this@WWLVideoUpNextFragment.getSpanSize(position)
            }
        }
    }

    @Suppress("NAME_SHADOWING")
    private fun getSpanSize(position: Int): Int {
        var position = position
        val spanSize = mLayoutManager?.spanCount ?: 1
        if (mIsLandscape) {
            return spanSize
        }
        if (position == 0) {
            return spanSize
        }
        position--
        return if (position == 0) {
            spanSize
        } else {
            1
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (mFragmentHost is FragmentHost) {
            mFragmentHost = activity as FragmentHost
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateLayoutIfNeed()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItem(item: WWLVideoDataset.DatasetItem?) {
        mAdapter?.apply {
            updateHeader(item)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateLayoutIfNeed() {
        var enable = true
        val orientation = resources.configuration.orientation
        mIsLandscape = orientation == Configuration.ORIENTATION_LANDSCAPE
        if (mIsLandscape) {
            enable = false
        }
        if (mIsLandscape) {
            mLayoutManager?.spanCount = 1
        } else {
            mLayoutManager?.spanCount = LWWLMusicUiUtil.getGridColumnCount(resources)
        }
        mAdapter?.apply {
            setHeader(enable)
            notifyDataSetChanged()
        }
    }

    private fun onItemClicked(item: WWLVideoDataset.DatasetItem) {
        mFragmentHost?.goToDetail(item)
    }

    inner class CustomAdapter(
        private val mDataSet: ArrayList<WWLVideoDataset.DatasetItem>
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var mHasHeader = true
        private val mTitle = "Up next"
        private var mHeaderItem: WWLVideoDataset.DatasetItem? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == HEADER) {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.wwl_video_card_header_item, parent, false)
                return HeaderViewHolder(v)
            }
            if (viewType == TITLE) {
                val v = LayoutInflater.from(parent.context)
                    .inflate(R.layout.wwl_video_card_title_item, parent, false)
                return TitleViewHolder(v)
            }
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.wwl_video_card_row_item, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            if (holder is HeaderViewHolder) {
                if (mHeaderItem != null) {
                    holder.liTitle.text = mHeaderItem?.title
                    holder.liSubtitle.text = mHeaderItem?.subtitle
                }
            } else if (holder is TitleViewHolder) {
                holder.liTitle.text = mTitle
            } else if (holder is ViewHolder) {
                holder.liTitle.text = getItem(position).title
                holder.liSubtitle.text = getItem(position).subtitle
            }
        }

        override fun getItemCount(): Int {
            return (if (mHasHeader) 1 else 0) + 1 + mDataSet.size
        }

        @Suppress("NAME_SHADOWING")
        override fun getItemViewType(position: Int): Int {
            var position = position
            if (mHasHeader) {
                if (position == 0) {
                    return HEADER
                }
                position--
            }
            return if (position == 0) {
                TITLE
            } else OTHER
        }

        fun setHeader(enable: Boolean) {
            mHasHeader = enable
        }

        fun updateHeader(item: WWLVideoDataset.DatasetItem?) {
            mHeaderItem = item
        }

        private fun getItem(position: Int): WWLVideoDataset.DatasetItem {
            return mDataSet[position - 1 - (if (mHasHeader) 1 else 0)]
        }

        internal inner class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val liTitle: TextView = v.findViewById(R.id.liTitle)
            val liSubtitle: TextView = v.findViewById(R.id.liSubtitle)
        }

        internal inner class TitleViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val liTitle: TextView = v.findViewById(R.id.liTitle)
        }

        internal inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val liTitle: TextView
            val liSubtitle: TextView

            init {
                v.setOnClickListener {
                    onItemClicked(
                        getItem(bindingAdapterPosition)
                    )
                }
                liTitle = v.findViewById(R.id.liTitle)
                liSubtitle = v.findViewById(R.id.liSubtitle)
            }
        }
    }
}

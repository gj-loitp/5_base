package vn.loitp.up.a.demo.rss

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.rss.RssItem
import vn.loitp.databinding.IRssBinding

@LogTag("RssItemsAdapter")
class RssItemsAdapter(
    private val onClick: ((RssItem) -> Unit)? = null
) : BaseAdapter() {

    private val itemList = ArrayList<RssItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<RssItem>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RSSViewHolder {
        val binding = IRssBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RSSViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is RSSViewHolder) {
            holder.bind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class RSSViewHolder(val binding: IRssBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rssItem: RssItem) {
            binding.tvTitle.text = rssItem.title
            binding.tvPubDate.text = rssItem.publishDate

            binding.ivThumb.loadGlide(
                any = rssItem.image,
            )
            binding.cardView.setOnClickListener {
                onClick?.invoke(rssItem)
            }
        }
    }
}

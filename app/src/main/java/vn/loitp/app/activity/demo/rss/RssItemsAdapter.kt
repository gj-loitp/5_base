package vn.loitp.app.activity.demo.rss

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.rss.RssItem
import kotlinx.android.synthetic.main.view_rss.view.*
import vn.loitp.app.R

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RSSViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_rss, parent, false)
        return RSSViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RSSViewHolder) {
            holder.bind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class RSSViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(rssItem: RssItem) {
            itemView.tvTitle.text = rssItem.title
            itemView.tvPubDate.text = rssItem.publishDate

            LImageUtil.load(
                context = itemView.ivThumb.context,
                any = rssItem.image,
                imageView = itemView.ivThumb
            )
            itemView.cardView.setOnClickListener {
                onClick?.invoke(rssItem)
            }
        }
    }
}

package vn.loitp.app.activity.demo.rss

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.utilities.LImageUtil
import com.rss.RssItem
import kotlinx.android.synthetic.main.row_rss_item.view.*
import vn.loitp.app.R
import java.util.*

@LogTag("RssItemsAdapter")
class RssItemsAdapter(
        private val onClick: ((RssItem) -> Unit)? = null
) : BaseAdapter() {

    private val itemList = ArrayList<RssItem>()

    fun setItems(items: List<RssItem>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RSSViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_rss_item, parent, false)
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

            LImageUtil.load(context = itemView.ivThumb.context, any = rssItem.image, imageView = itemView.ivThumb)
            itemView.cardView.setOnClickListener {
                onClick?.invoke(rssItem)
            }
        }
    }
}

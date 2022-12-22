package vn.loitp.app.a.customviews.recyclerview.concatAdapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_item_news.view.*
import vn.loitp.R
import vn.loitp.app.a.customviews.recyclerview.concatAdapter.data.model.News

@LogTag("NewsAdapter")
class NewsAdapter(
    private val listNews: ArrayList<News>
) : BaseAdapter() {

    var onClickRootListener: ((News, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addData(listNews: ArrayList<News>) {
        this.listNews.addAll(listNews)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: News) {
            itemView.textViewNews.text = news.title
            LImageUtil.load(
                context = itemView.imageView.context,
                any = news.image,
                imageView = itemView.imageView
            )
            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(news, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_news, parent,
                false
            )
        )

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(listNews[position])
        }
    }
}

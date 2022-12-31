package vn.loitp.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.i_news.view.*
import vn.loitp.R
import vn.loitp.a.cv.rv.concatAdapter.data.model.News

@LogTag("NewsAdapter")
class NewsAdapter(
    private val listNews: ArrayList<News>
) : BaseAdapter() {

    var onClickRootListener: ((News, Int) -> Unit)? = null

    fun addData(listNews: ArrayList<News>) {
        this.listNews.addAll(listNews)
        notifyItemRangeChanged(0, itemCount)
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                /* resource = */ R.layout.i_news,
                /* root = */ parent,
                /* attachToRoot = */ false
            )
        )

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listNews[position])
        }
    }
}

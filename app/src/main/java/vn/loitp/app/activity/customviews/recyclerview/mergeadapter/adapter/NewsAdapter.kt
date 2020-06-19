package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.core.utilities.LLog
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_item_news.view.*
import kotlinx.android.synthetic.main.view_row_item_user.view.layoutRoot
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.News

class NewsAdapter(
        private val listNews: ArrayList<News>
) : RecyclerView.Adapter<NewsAdapter.DataViewHolder>() {
    private val TAG = "loitpp" + javaClass.simpleName
    var onClickRootListener: ((News, Int) -> Unit)? = null

    fun addData(listNews: ArrayList<News>) {
        this.listNews.addAll(listNews)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: News) {
            LLog.d(TAG, "bind $bindingAdapterPosition")
            itemView.textViewNews.text = news.title
            LImageUtil.load(context = itemView.imageView.context,
                    url = news.image,
                    imageView = itemView.imageView)
            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(news, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.view_row_item_news, parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listNews.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
            holder.bind(listNews[position])

}

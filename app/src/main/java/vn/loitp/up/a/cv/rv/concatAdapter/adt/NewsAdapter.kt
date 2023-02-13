package vn.loitp.up.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.INewsBinding
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.News

@LogTag("NewsAdapter")
class NewsAdapter(
    private val listNews: ArrayList<News>
) : BaseAdapter() {

    var onClickRootListener: ((News, Int) -> Unit)? = null

    fun addData(listNews: ArrayList<News>) {
        this.listNews.addAll(listNews)
        notifyItemRangeChanged(0, itemCount)
    }

    inner class DataViewHolder(val binding: INewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.textViewNews.text = news.title
            binding.imageView.loadGlide(
                any = news.image,
            )
            binding.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(news, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = INewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

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

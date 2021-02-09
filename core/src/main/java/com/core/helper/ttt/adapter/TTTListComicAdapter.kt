package com.core.helper.ttt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.helper.ttt.model.comic.Comic
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_ttt_comic.view.*

@LogTag("TTTListComicAdapter")
class TTTListComicAdapter : BaseAdapter() {

    private val listComic = ArrayList<Comic>()
    var onClickRootListener: ((Comic, Int) -> Unit)? = null

    fun setData(listComic: ArrayList<Comic>) {
        this.listComic.clear()
        this.listComic.addAll(listComic)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comic: Comic) {
            if (comic.urlImg.isEmpty()) {
                itemView.ivCover.visibility = View.GONE
            } else {
                itemView.ivCover.visibility = View.VISIBLE
                LImageUtil.load(
                        context = itemView.context,
                        any = comic.urlImg,
                        imageView = itemView.ivCover
                )
            }
            itemView.tvTitle.text = comic.title
            LUIUtil.setSafeOnClickListenerElastic(
                    view = itemView.cardView,
                    runnable = {
                        onClickRootListener?.invoke(comic, bindingAdapterPosition)
                    })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.view_row_ttt_comic,
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listComic.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(listComic[position])
        }
    }

}

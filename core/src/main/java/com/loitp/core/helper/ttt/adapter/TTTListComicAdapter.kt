package com.loitp.core.helper.ttt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitpcore.R
import com.loitp.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_ttt_comic.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTListComicAdapter")
class TTTListComicAdapter : BaseAdapter() {

    private val listComic = ArrayList<Comic>()
    var onClickRootListener: ((Comic, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
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
                }
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                /* resource = */ R.layout.view_row_ttt_comic,
                /* root = */ parent,
                /* attachToRoot = */ false
            )
        )

    override fun getItemCount(): Int = listComic.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is com.loitp.core.helper.ttt.adapter.TTTListComicAdapter.DataViewHolder) {
            holder.bind(listComic[position])
        }
    }
}

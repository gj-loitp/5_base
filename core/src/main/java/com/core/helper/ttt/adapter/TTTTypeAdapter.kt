package com.core.helper.ttt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.helper.ttt.model.comictype.ComicType
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_ttt_select_type.view.*

@LogTag("TTTTypeAdapter")
class TTTTypeAdapter() : BaseAdapter() {

    private val listComicType = ArrayList<ComicType>()
    private var currentComicType: ComicType? = null
    var onClickRootListener: ((ComicType, Int) -> Unit)? = null

    fun setData(listComicType: ArrayList<ComicType>, currentComicType: ComicType?) {
        this.listComicType.clear()
        this.listComicType.addAll(listComicType)
        this.currentComicType = currentComicType
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comicType: ComicType) {
            if (comicType.url == currentComicType?.url) {
                itemView.ivFav.visibility = View.VISIBLE
            } else {
                itemView.ivFav.visibility = View.GONE
            }
            itemView.tvType.text = comicType.type
            LUIUtil.setSafeOnClickListenerElastic(
                    view = itemView.cardView,
                    runnable = {
                        onClickRootListener?.invoke(comicType, bindingAdapterPosition)
                    })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.view_row_ttt_select_type,
                            parent,
                            false
                    )
            )

    override fun getItemCount(): Int = listComicType.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(listComicType[position])
        }
    }

}

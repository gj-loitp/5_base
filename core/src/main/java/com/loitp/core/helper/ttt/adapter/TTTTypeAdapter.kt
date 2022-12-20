package com.loitp.core.helper.ttt.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.helper.ttt.model.comictype.ComicType
import com.loitpcore.R
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_ttt_select_type.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("TTTTypeAdapter")
class TTTTypeAdapter : BaseAdapter() {

    private val listComicType = ArrayList<ComicType>()
    private var currentComicType: ComicType? = null
    var onClickRootListener: ((ComicType, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(
        listComicType: ArrayList<ComicType>,
        currentComicType: ComicType?
    ) {
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
                /* resource = */ R.layout.view_row_ttt_select_type,
                /* root = */ parent,
                /* attachToRoot = */ false
            )
        )

    override fun getItemCount(): Int = listComicType.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listComicType[position])
        }
    }
}

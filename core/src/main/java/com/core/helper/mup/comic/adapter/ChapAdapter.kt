package com.core.helper.mup.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.helper.mup.comic.model.Chap
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_comic_chap.view.*

@LogTag("CategoryAdapter")
class ChapAdapter : AnimationAdapter() {

    private val listChap = ArrayList<Chap>()

    fun setListData(listChap: List<Chap>) {
        this.listChap.addAll(listChap)
        notifyDataSetChanged()
    }

    var onClickRoot: ((Chap) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chap: Chap) {
            itemView.tvChap.text = chap.title
            itemView.tvChap.setSafeOnClickListener {
                onClickRoot?.invoke(chap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic_chap, parent,
                    false
            ))

    override fun getItemCount(): Int = listChap.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(chap = listChap[position])
        }
    }

}

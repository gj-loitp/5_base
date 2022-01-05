package com.core.helper.mup.comic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_comic_progress.view.*

@LogTag("GirlProgressAdapter")
class ComicProgressAdapter(
    val heightRootView: Int? = null
) : BaseAdapter() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            heightRootView?.let {
                LUIUtil.setSizeOfView(view = itemView.layoutRootView, height = heightRootView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_row_comic_progress, parent,
                false
            )
        )

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }
}

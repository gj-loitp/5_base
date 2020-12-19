package com.core.helper.mup.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter

@LogTag("GirlProgressAdapter")
class GirlProgressAdapter : BaseAdapter() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_progress, parent,
                    false
            ))

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

}

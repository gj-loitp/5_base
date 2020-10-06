package com.core.helper.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_girl_title.view.*

@LogTag("GirlTitleAdapter")
class GirlTitleAdapter : AnimationAdapter() {
    private var title: String = ""
    private var marginStartEndPx: Int? = null
    private var marginTopPx: Int? = null

    fun setTitle(title: String) {
        this.title = title
        notifyDataSetChanged()
    }

    fun setMargin(marginStartEnd: Int?, marginTop: Int?) {
        this.marginStartEndPx = marginStartEnd
        this.marginTopPx = marginTop
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.tvTitle.text = title
            marginStartEndPx?.let { leftRight ->
                marginTopPx?.let { top ->
                    LUIUtil.setMargins(view = itemView.roundRect, leftPx = leftRight, topPx = top, rightPx = leftRight, bottomPx = 0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_title, parent,
                    false
            ))

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

}

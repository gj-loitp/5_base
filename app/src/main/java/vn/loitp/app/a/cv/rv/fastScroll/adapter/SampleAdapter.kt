package vn.loitp.app.a.cv.rv.fastScroll.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R
import vn.loitp.app.a.cv.rv.fastScroll.db.ListItem

class SampleAdapter(
    private val listItem: List<ListItem>
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_DATA = 1
    }

    private val containsHeaders: Boolean = listItem.any { it is ListItem.HeaderItem }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    VIEW_TYPE_HEADER -> R.layout.layout_fast_scroll_header_item
                    VIEW_TYPE_DATA -> R.layout.layout_fast_scroll_data_item
                    else -> throw IllegalArgumentException()
                },
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listItem.count()

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            is ListItem.HeaderItem -> VIEW_TYPE_HEADER
            is ListItem.DataItem -> VIEW_TYPE_DATA
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView as TextView

        @SuppressLint("UseCompatTextViewDrawableApis")
        fun bind(listItem: ListItem) {
            when (listItem) {
                is ListItem.HeaderItem -> {
                    titleView.text = listItem.title
                    titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        listItem.iconRes,
                        0,
                        0,
                        0
                    )
                    val iconColor = titleView.textColors

                    titleView.compoundDrawableTintList = iconColor
                }
                is ListItem.DataItem -> {
                    titleView.text = listItem.title
                    if (containsHeaders) {
                        titleView.updatePaddingRelative(
                            start = titleView.context.resources.getDimensionPixelSize(
                                R.dimen.list_with_headers_start_padding
                            )
                        )
                    }
                }
            }
        }
    }
}

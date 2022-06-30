package com.loitpcore.views.calendar.cosmocalendar.selection.selectionbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.R
import com.loitpcore.views.calendar.cosmocalendar.model.Day
import com.loitpcore.views.calendar.cosmocalendar.view.CalendarView
import com.loitpcore.views.calendar.cosmocalendar.view.customviews.CircleAnimationTextView

class MultipleSelectionBarAdapter(
    calendarView: CalendarView,
    listItemClickListener: ListItemClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface ListItemClickListener {
        fun onMultipleSelectionListItemClick(day: Day)
    }

    companion object {
        private const val VIEW_TYPE_TITLE = 0
        private const val VIEW_TYPE_CONTENT = 1
    }

    private var items: List<SelectionBarItem>
    private val calendarView: CalendarView
    private var listItemClickListener: ListItemClickListener?

    init {
        items = ArrayList()
        this.calendarView = calendarView
        this.listItemClickListener = listItemClickListener
    }

    fun setData(items: List<SelectionBarItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun setListItemClickListener(listItemClickListener: ListItemClickListener?) {
        this.listItemClickListener = listItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TITLE) {
            TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_cosmo_calendar_item_multiple_selection_bar_title,
                        parent,
                        false
                    )
            )
        } else {
            ContentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.view_cosmo_calendar_item_multiple_selection_bar_content,
                        parent,
                        false
                    )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is SelectionBarTitleItem) {
            VIEW_TYPE_TITLE
        } else {
            VIEW_TYPE_CONTENT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_TITLE) {
            (holder as TitleViewHolder).bind(position)
        } else {
            (holder as ContentViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)

        fun bind(position: Int) {
            val selectionBarTitleItem = items[position] as SelectionBarTitleItem
            tvTitle.text = selectionBarTitleItem.title
            tvTitle.setTextColor(calendarView.selectionBarMonthTextColor)
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val catvDay: CircleAnimationTextView = itemView.findViewById(R.id.catv_day)

        fun bind(position: Int) {
            val selectionBarContentItem = items[position] as SelectionBarContentItem
            catvDay.text = selectionBarContentItem.day.dayNumber.toString()
            catvDay.setTextColor(calendarView.selectedDayTextColor)
            catvDay.showAsSingleCircle(calendarView)

            itemView.setOnClickListener {
                listItemClickListener?.onMultipleSelectionListItemClick(selectionBarContentItem.day)
            }
        }
    }
}

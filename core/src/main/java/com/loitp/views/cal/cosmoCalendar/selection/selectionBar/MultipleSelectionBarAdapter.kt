package com.loitp.views.cal.cosmoCalendar.selection.selectionBar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loitp.views.cal.cosmoCalendar.model.Day
import com.loitp.views.cal.cosmoCalendar.view.CalendarView
import com.loitp.views.cal.cosmoCalendar.view.customViews.CircleAnimationTextView
import com.loitp.R

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<SelectionBarItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    @Suppress("unused")
    fun setListItemClickListener(listItemClickListener: ListItemClickListener?) {
        this.listItemClickListener = listItemClickListener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_TITLE) {
            TitleViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        /* resource = */ R.layout.l_v_cosmo_calendar_item_multiple_selection_bar_title,
                        /* root = */ parent,
                        /* attachToRoot = */ false
                    )
            )
        } else {
            ContentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.l_v_cosmo_calendar_item_multiple_selection_bar_content,
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

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
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

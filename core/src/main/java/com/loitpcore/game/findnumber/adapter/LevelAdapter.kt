package com.loitpcore.game.findnumber.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.R
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.game.findnumber.db.Db.Companion.STATUS_LEVEL_OPEN
import com.loitpcore.game.findnumber.db.Db.Companion.STATUS_LEVEL_WIN
import com.loitpcore.game.findnumber.model.Level
import kotlinx.android.synthetic.main.view_row_item_find_number_level.view.*

@LogTag("LevelAdapter")
class LevelAdapter() : BaseAdapter() {

    private val listLevel = ArrayList<Level>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListLevel(listLevel: ArrayList<Level>) {
        this.listLevel.clear()
        this.listLevel.addAll(listLevel)
        notifyDataSetChanged()
    }

    var onClickRootView: ((Level, View) -> Unit)? = null

    inner class LevelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(level: Level) {

            itemView.tvLevel.text = "${level.name}"
            LUIUtil.setTextShadow(textView = itemView.tvLevel, color = Color.BLACK)

            if (level.status == STATUS_LEVEL_OPEN) {
                itemView.layoutRootView.setBackgroundResource(R.drawable.bkg_blue_2)
            } else if (level.status == STATUS_LEVEL_WIN) {
                itemView.layoutRootView.setBackgroundResource(R.drawable.bkg_yellow)
            }

            LUIUtil.setOnClickListenerElastic(
                view = itemView.layoutRootView,
                runnable = {
                    onClickRootView?.invoke(level, itemView.layoutRootView)
                }
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_row_item_find_number_level, parent, false)
        return LevelViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LevelViewHolder) {
            holder.bind(listLevel[position])
        }
    }

    override fun getItemCount(): Int {
        return listLevel.size
    }
}

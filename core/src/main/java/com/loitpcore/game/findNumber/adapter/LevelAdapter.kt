package com.loitpcore.game.findNumber.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.R
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.game.findNumber.db.Db.Companion.STATUS_LEVEL_OPEN
import com.loitpcore.game.findNumber.db.Db.Companion.STATUS_LEVEL_WIN
import com.loitpcore.game.findNumber.model.Level
import kotlinx.android.synthetic.main.view_row_item_find_number_level.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("LevelAdapter")
class LevelAdapter : BaseAdapter() {

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

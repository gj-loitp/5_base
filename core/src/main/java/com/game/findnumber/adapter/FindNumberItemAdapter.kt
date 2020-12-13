package com.game.findnumber.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.utilities.LUIUtil
import com.game.findnumber.model.FindNumberItem
import kotlinx.android.synthetic.main.view_row_item_find_number.view.*

@LogTag("NetAdapter")
class FindNumberItemAdapter() : AnimationAdapter() {

    private val listFindNumberItem = ArrayList<FindNumberItem>()

    fun setListFindNumberItem(listNet: ArrayList<FindNumberItem>) {
        this.listFindNumberItem.clear()
        this.listFindNumberItem.addAll(listNet)
        notifyDataSetChanged()
    }

//    fun addFindNumberItem(findNumberItem: FindNumberItem) {
//        this.listFindNumberItem.add(element = findNumberItem)
//        notifyItemChanged(itemCount - 1)
//    }
//
//    fun clear() {
//        this.listFindNumberItem.clear()
//        notifyDataSetChanged()
//    }

    var onClickRootView: ((FindNumberItem) -> Unit)? = null

    inner class NetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(findNumberItem: FindNumberItem) {
            itemView.textView.text = findNumberItem.name
            LUIUtil.setTextShadow(textView = itemView.textView, color = Color.BLACK)

            //TODO loitpp custom background
            itemView.ivBkg.setBackgroundResource(R.drawable.flute_k5)

            LUIUtil.setSafeOnClickListenerElastic(
                    view = itemView.layoutRootView,
                    runnable = {
                        onClickRootView?.invoke(findNumberItem)
                    })
            setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_find_number, parent, false)
        return NetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NetViewHolder) {
            holder.bind(listFindNumberItem[position])
        }
    }

    override fun getItemCount(): Int {
        return listFindNumberItem.size
    }
}

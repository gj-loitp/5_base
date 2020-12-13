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

    private val listNet = ArrayList<FindNumberItem>()

//    fun setListNet(listNet: ArrayList<Net>) {
//        this.listNet.clear()
//        this.listNet.addAll(listNet)
//        notifyDataSetChanged()
//    }

    fun addNet(findNumberItem: FindNumberItem) {
        this.listNet.add(element = findNumberItem)
        notifyItemChanged(itemCount - 1)
    }

    fun clear() {
        this.listNet.clear()
        notifyDataSetChanged()
    }

    var onClickRootView: ((FindNumberItem) -> Unit)? = null

    inner class NetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(findNumberItem: FindNumberItem) {
//            itemView.rootView.layoutParams.width = sizeW
//            itemView.rootView.layoutParams.height = sizeH
//            itemView.rootView.invalidate()

            itemView.textView.text = findNumberItem.name
            LUIUtil.setTextShadow(textView = itemView.textView, color = Color.BLACK)

            //setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)

            itemView.layoutRootView.setOnClickListener {
                onClickRootView?.invoke(findNumberItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_find_number, parent, false)
        return NetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NetViewHolder) {
            holder.bind(listNet[position])
        }
    }

    override fun getItemCount(): Int {
        return listNet.size
    }
}

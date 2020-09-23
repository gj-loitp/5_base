package com.core.helper.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.adapter.AnimationAdapter
import com.core.helper.girl.model.GirlPage
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_row_girl_header.view.*

class GirlHeaderAdapter : AnimationAdapter() {
    private val logTag = javaClass.simpleName

    private var girlPage: GirlPage? = null
//    var onClickRootListener: ((GirlPage, Int) -> Unit)? = null

    fun setData(girlPage: GirlPage) {
        this.girlPage = girlPage
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(girlPage: GirlPage) {
            LImageUtil.load(context = itemView.imageView.context, url = girlPage.src, imageView = itemView.imageView)
//            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_header, parent,
                    false
            ))

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            girlPage?.let {
                holder.bind(girlPage = it)
            }
        }
    }

}

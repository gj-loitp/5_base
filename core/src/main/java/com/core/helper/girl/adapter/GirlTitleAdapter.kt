package com.core.helper.girl.adapter

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.adapter.AnimationAdapter
import kotlinx.android.synthetic.main.view_row_girl_title.view.*

class GirlTitleAdapter : AnimationAdapter() {
    private val logTag = javaClass.simpleName
    private var title: String = ""

    fun setTitle(title: String) {
        this.title = title
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.tvTitle.text = title
            ValueAnimator.ofFloat(0f, 200f, 0f).apply {
                addUpdateListener { animation -> itemView.roundRect.bottomRightRadius = (animation.animatedValue as Float) }
                duration = 800
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
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

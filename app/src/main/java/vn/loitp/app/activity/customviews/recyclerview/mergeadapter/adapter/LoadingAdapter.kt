package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.adapter.AnimationAdapter
import vn.loitp.app.R

class LoadingAdapter : AnimationAdapter() {
    private val logTag = javaClass.simpleName

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
//            LLog.d(logTag, "bind $bindingAdapterPosition")
            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_loading, parent, false))

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind()
        }
    }

}

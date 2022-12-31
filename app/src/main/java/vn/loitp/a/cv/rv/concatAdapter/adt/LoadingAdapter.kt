package vn.loitp.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import vn.loitp.R

@LogTag("LoadingAdapter")
class LoadingAdapter : BaseAdapter() {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            // do sth
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    /* resource = */ R.layout.i_loading,
                    /* root = */ parent,
                    /* attachToRoot = */ false
                )
        )

    override fun getItemCount(): Int = 1
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind()
        }
    }
}

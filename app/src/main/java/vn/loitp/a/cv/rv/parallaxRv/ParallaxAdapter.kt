package vn.loitp.a.cv.rv.parallaxRv

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import vn.loitp.R

class ParallaxAdapter internal constructor(private val context: Context) : BaseAdapter() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        position: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.i_parallax, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return 50
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bind() {
            // do sth
        }
    }
}

package vn.loitp.app.activity.customviews.recyclerview.turnLayoutManager

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.turnLayoutManager.SampleAdapter.SampleViewHolder

class SampleAdapter(context: Context) : RecyclerView.Adapter<SampleViewHolder>() {
    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SampleViewHolder {
        val sampleView = layoutInflater.inflate(R.layout.view_sample_tlm, parent, false) as TextView
        return SampleViewHolder(sampleView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.tv.text = "\$position"
    }

    override fun getItemCount(): Int {
        return 31
    }

    class SampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView

        init {
            tv = itemView as TextView
        }
    }
}
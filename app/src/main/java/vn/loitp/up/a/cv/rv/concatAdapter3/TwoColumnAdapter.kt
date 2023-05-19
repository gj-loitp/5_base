package vn.loitp.up.a.cv.rv.concatAdapter3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.VConcatAdapter3TwoColumnBinding

class TwoColumnAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<TwoColumnViewHolder>() {
    private var data: List<String> = emptyList()

    companion object {
        const val VIEW_TYPE = 2222
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TwoColumnViewHolder {
        return TwoColumnViewHolder(
            VConcatAdapter3TwoColumnBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    override fun onBindViewHolder(holder: TwoColumnViewHolder, position: Int) {
        holder.bind(data[position], onClick)
    }

    override fun getItemCount(): Int = data.size

    fun updateData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }
}

package vn.loitp.up.a.cv.rv.concatAdapter3

import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.VConcatAdapte3HorizontalBinding

class HorizontalViewHolder(
    private val binding: VConcatAdapte3HorizontalBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String, onClick: (String) -> Unit) {
        binding.textView.text = data
        binding.root.setOnClickListener { onClick(data) }
    }
}

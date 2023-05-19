package vn.loitp.up.a.cv.rv.concatAdapter3

import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.VConcatAdapter3OneColumnBinding

class OneColumnViewHolder(
    private val binding: VConcatAdapter3OneColumnBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String, onClick: (String) -> Unit) {
        binding.textView.text = data
        binding.root.setOnClickListener { onClick(data) }
    }
}

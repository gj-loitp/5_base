package vn.loitp.up.a.cv.rv.concatAdapter3

import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.ViewHorizontalBinding

class HorizontalViewHolder(
    private val binding: ViewHorizontalBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String, onClick: (String) -> Unit) {
        binding.textView.text = data
        binding.root.setOnClickListener { onClick(data) }
    }
}

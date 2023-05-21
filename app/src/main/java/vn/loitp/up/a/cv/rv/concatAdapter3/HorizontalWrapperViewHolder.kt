package vn.loitp.up.a.cv.rv.concatAdapter3

import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.databinding.VConcatAdapter3HorizontalWrapperBinding

class HorizontalWrapperViewHolder(
    private val binding: VConcatAdapter3HorizontalWrapperBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(adapter: HorizontalAdapter, lastScrollX: Int, onScrolled: (Int) -> Unit) {
        val context = binding.root.context
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.doOnPreDraw {
            binding.recyclerView.scrollBy(lastScrollX, 0)
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrolled(recyclerView.computeHorizontalScrollOffset())
            }
        })
    }
}

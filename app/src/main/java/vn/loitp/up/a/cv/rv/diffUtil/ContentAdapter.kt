package vn.loitp.up.a.cv.rv.diffUtil

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.ViewItemDiffUtilBinding
import kotlin.properties.Delegates

class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder>(), AutoUpdatableAdapter {
//    val logTag: String = javaClass.simpleName

    var items: List<Content> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding =
            ViewItemDiffUtilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        holder.bind(items[position])
    }

    class ViewHolder(val binding: ViewItemDiffUtilBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(content: Content) = with(itemView) {
            binding.image.loadGlide(any = content.image)
            binding.text.text = content.text
        }
    }
}

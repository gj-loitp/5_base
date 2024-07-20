package vn.loitp.up.a.picker.lassi

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lassi.common.extenstions.loadImage
import com.lassi.common.extenstions.toBinding
import com.lassi.common.utils.ImageUtils
import com.lassi.data.media.MiMedia
import vn.loitp.databinding.RowSelectedMediaBinding

class SelectedMediaAdapter(private val onItemClicked: (miMedia: MiMedia) -> Unit) :
    RecyclerView.Adapter<SelectedMediaAdapter.MediaViewHolder>() {

    private val selectedMedias = ArrayList<MiMedia>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(selectedMedias: List<MiMedia>?) {
        selectedMedias?.let {
            this.selectedMedias.clear()
            this.selectedMedias.addAll(selectedMedias)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(parent.toBinding())
    }

    override fun getItemCount() = this.selectedMedias.size

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.bind(this.selectedMedias[position])
    }

    inner class MediaViewHolder(private val binding: RowSelectedMediaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(miMedia: MiMedia) {
            binding.ivSelectedMediaThumbnail.loadImage(ImageUtils.getThumb(miMedia))
            binding.root.setOnClickListener {
                onItemClicked(miMedia)
            }
        }
    }
}

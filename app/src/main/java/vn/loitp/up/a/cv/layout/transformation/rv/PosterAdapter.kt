package vn.loitp.up.a.cv.layout.transformation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.skydoves.transformationlayout.TransformationLayout
import vn.loitp.databinding.ITransformationPosterBinding

class PosterAdapter(
    val onClick: ((Poster, TransformationLayout) -> Unit)? = null
) : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ITransformationPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is PosterViewHolder) {
            holder.bind(listPoster[position])
        }
    }

    fun addPosterList(list: List<Poster>) {
        listPoster.clear()
        listPoster.addAll(list)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun getItemCount() = listPoster.size

    inner class PosterViewHolder(val binding: ITransformationPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poster: Poster) {

            itemView.run {
                binding.ivItemPosterPost.loadGlide(
                    any = poster.poster,
                )

                binding.tvItemPosterTitle.text = poster.name
                binding.tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    onClick?.invoke(poster, binding.layoutItemPosterTransformation)
                }
            }
        }
    }
}

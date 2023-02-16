package vn.loitp.up.a.cv.layout.transformation.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.ITransformationPosterMenuBinding
import vn.loitp.up.a.cv.layout.transformation.TransformationDetailActivity

class PosterMenuAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ITransformationPosterMenuBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PosterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is PosterViewHolder) {
            holder.bind(poster = listPoster[position])
        }
    }

    fun addPosterList(listPoster: List<Poster>) {
        this.listPoster.clear()
        this.listPoster.addAll(listPoster)
        notifyItemRangeChanged(0, itemCount)
    }

    override fun getItemCount() = listPoster.size

    inner class PosterViewHolder(val binding: ITransformationPosterMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poster: Poster) {
            itemView.run {
                binding.ivItemPosterPost.loadGlide(
                    any = poster.poster,
                )
                binding.tvItemPosterTitle.text = poster.name

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= binding.layoutItemPosterMenuTransformation.duration) {
                        TransformationDetailActivity.startActivity(
                            context = context,
                            transformationLayout = binding.layoutItemPosterMenuTransformation,
                            poster = poster
                        )
                        previousTime = now
                    }
                }
            }
        }
    }
}

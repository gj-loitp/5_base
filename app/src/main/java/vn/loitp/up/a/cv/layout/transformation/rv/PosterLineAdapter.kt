package vn.loitp.up.a.cv.layout.transformation.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.ITransformationPosterLineBinding
import vn.loitp.up.a.cv.layout.transformation.TransformationDetailActivity

class PosterLineAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ITransformationPosterLineBinding.inflate(
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

    inner class PosterViewHolder(val binding: ITransformationPosterLineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(poster: Poster) {
            itemView.run {
                binding.ivItemPosterPost.loadGlide(
                    any = poster.poster,
                )

                binding.tvItemPosterTitle.text = poster.name
                binding.tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= binding.layoutItemPosterLineTransformation.duration) {
                        TransformationDetailActivity.startActivity(
                            context = context,
                            transformationLayout = binding.layoutItemPosterLineTransformation,
                            poster = poster
                        )
                        previousTime = now
                    }
                }
            }
        }
    }
}

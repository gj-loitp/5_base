package vn.loitp.up.a.cv.layout.transformation.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.skydoves.transformationlayout.TransformationLayout
import vn.loitp.databinding.ITransformationPosterBinding

class PosterSingleAdapter constructor(
    private val delegate: PosterDelegate
) : BaseAdapter() {

    interface PosterDelegate {
        fun onItemClick(poster: Poster, itemView: TransformationLayout)
    }

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ITransformationPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class PosterViewHolder(val binding: ITransformationPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(poster: Poster) {
            itemView.run {
                binding.ivItemPosterPost.loadGlide(
                    any = poster.poster,
                )

                binding.tvItemPosterTitle.text = poster.name
                binding.tvItemPosterRunningTime.text = poster.playtime

                // sets a transition name to the transformation layout.
                // this code must not be in listener.
                binding.layoutItemPosterTransformation.transitionName = poster.name

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= binding.layoutItemPosterTransformation.duration) {
                        delegate.onItemClick(
                            poster = poster,
                            itemView = binding.layoutItemPosterTransformation
                        )
                        previousTime = now
                    }
                }
            }
        }
    }
}

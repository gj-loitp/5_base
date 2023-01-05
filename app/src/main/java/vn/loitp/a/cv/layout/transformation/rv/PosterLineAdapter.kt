package vn.loitp.a.cv.layout.transformation.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.i_transformation_poster_line.view.*
import vn.loitp.R
import vn.loitp.a.cv.layout.transformation.TransformationDetailActivityFont

class PosterLineAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                /* resource = */ R.layout.i_transformation_poster_line,
                /* root = */ parent,
                /* attachToRoot = */ false
            )
        )
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

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(poster: Poster) {
            itemView.run {
                LImageUtil.load(
                    context = context,
                    any = poster.poster,
                    imageView = ivItemPosterPost
                )

                tvItemPosterTitle.text = poster.name
                tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterLineTransformation.duration) {
                        TransformationDetailActivityFont.startActivity(
                            context,
                            layoutItemPosterLineTransformation,
                            poster
                        )
                        previousTime = now
                    }
                }
            }
        }
    }
}

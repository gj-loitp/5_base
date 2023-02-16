package vn.loitp.up.a.cv.layout.transformation.rv

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import kotlinx.android.synthetic.main.i_transformation_poster_menu.view.*
import vn.loitp.R
import vn.loitp.up.a.cv.layout.transformation.TransformationDetailActivity

class PosterMenuAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                /* resource = */ R.layout.i_transformation_poster_menu,
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
                ivItemPosterPost.loadGlide(
                    any = poster.poster,
                )
                tvItemPosterTitle.text = poster.name

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterMenuTransformation.duration) {
                        TransformationDetailActivity.startActivity(
                            context,
                            layoutItemPosterMenuTransformation,
                            poster
                        )
                        previousTime = now
                    }
                }
            }
        }
    }
}

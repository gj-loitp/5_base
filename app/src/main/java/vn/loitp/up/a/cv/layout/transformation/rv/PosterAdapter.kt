package vn.loitp.up.a.cv.layout.transformation.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.skydoves.transformationlayout.TransformationLayout
import kotlinx.android.synthetic.main.i_transformation_poster.view.*
import vn.loitp.R

class PosterAdapter(
    val onClick: ((Poster, TransformationLayout) -> Unit)? = null
) : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                /* resource = */ R.layout.i_transformation_poster,
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
            holder.bind(listPoster[position])
        }
    }

    fun addPosterList(list: List<Poster>) {
        listPoster.clear()
        listPoster.addAll(list)
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
                tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    onClick?.invoke(poster, layoutItemPosterTransformation)
                }
            }
        }
    }
}

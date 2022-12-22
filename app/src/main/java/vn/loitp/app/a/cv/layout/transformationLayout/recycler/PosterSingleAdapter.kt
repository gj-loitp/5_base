package vn.loitp.app.a.cv.layout.transformationLayout.recycler

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import com.skydoves.transformationlayout.TransformationLayout
import kotlinx.android.synthetic.main.view_item_transformation_poster.view.*
import vn.loitp.R

class PosterSingleAdapter constructor(
    private val delegate: PosterDelegate
) : BaseAdapter() {

    interface PosterDelegate {
        fun onItemClick(poster: Poster, itemView: TransformationLayout)
    }

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                R.layout.view_item_transformation_poster,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bind(poster = listPoster[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPosterList(listPoster: List<Poster>) {
        this.listPoster.clear()
        this.listPoster.addAll(listPoster)
        notifyDataSetChanged()
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

                // sets a transition name to the transformation layout.
                // this code must not be in listener.
                layoutItemPosterTransformation.transitionName = poster.name

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterTransformation.duration) {
                        delegate.onItemClick(poster, layoutItemPosterTransformation)
                        previousTime = now
                    }
                }
            }
        }
    }
}

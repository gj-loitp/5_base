package vn.loitp.app.activity.customviews.layout.transformationLayout.recycler

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_item_transformation_poster_line.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationLayout.TransformationDetailActivity

class PosterLineAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(
            inflater.inflate(
                R.layout.view_item_transformation_poster_line,
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

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterLineTransformation.duration) {
                        TransformationDetailActivity.startActivity(
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

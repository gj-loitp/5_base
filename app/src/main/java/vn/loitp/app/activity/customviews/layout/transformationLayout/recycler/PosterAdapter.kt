package vn.loitp.app.activity.customviews.layout.transformationLayout.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import com.skydoves.transformationlayout.TransformationLayout
import kotlinx.android.synthetic.main.view_item_transformation_poster.view.*
import vn.loitp.R

class PosterAdapter(
    val onClick: ((Poster, TransformationLayout) -> Unit)? = null
) : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()

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
            holder.bind(listPoster[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPosterList(list: List<Poster>) {
        listPoster.clear()
        listPoster.addAll(list)
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
                    onClick?.invoke(poster, layoutItemPosterTransformation)
                }
            }
        }
    }
}

package vn.loitp.app.activity.customviews.simpleRatingBar

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.willy.ratingbar.BaseRatingBar
import com.willy.ratingbar.ScaleRatingBar
import vn.loitp.R
import vn.loitp.app.activity.customviews.simpleRatingBar.SRBAdapter.MyViewHolder

class SRBAdapter(private val mContext: Context) : RecyclerView.Adapter<MyViewHolder>() {
    private val list: MutableList<Float>

    init {
        list = ArrayList()
        for (i in 1..19) {
            list.add(i % 6f)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        @SuppressLint("InflateParams") val view =
            LayoutInflater.from(mContext).inflate(R.layout.layout_item_srb, null)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ratingBar.tag = position
        holder.ratingBar.rating = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ratingBar: ScaleRatingBar

        init {
            ratingBar = itemView.findViewById(R.id.ratingBar)
            ratingBar.setOnRatingChangeListener { ratingBar: BaseRatingBar, rating: Float, _: Boolean ->
                val position = ratingBar.tag as Int
                list[position] = rating
            }
        }
    }
}

package vn.loitp.app.activity.customviews.recyclerview.concatadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_row_item_banner.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.concatadapter.data.model.Banner

@LogTag("BannerAdapter")
class BannerAdapter(
        private val listBanner: ArrayList<Banner>
) : AnimationAdapter() {

    fun setData(listBanner: ArrayList<Banner>) {
        this.listBanner.clear()
        this.listBanner.addAll(listBanner)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Banner) {
            LImageUtil.load(context = itemView.imageViewBanner.context,
                    drawableRes = user.bannerImage,
                    imageView = itemView.imageViewBanner)
            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_banner, parent, false))

    override fun getItemCount(): Int = listBanner.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(listBanner[position])
        }
    }

}

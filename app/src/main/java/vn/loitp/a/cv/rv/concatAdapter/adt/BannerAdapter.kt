package vn.loitp.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.i_banner.view.*
import vn.loitp.R
import vn.loitp.a.cv.rv.concatAdapter.data.model.Banner

@LogTag("BannerAdapter")
class BannerAdapter(
    private val listBanner: ArrayList<Banner>
) : BaseAdapter() {

    fun setData(listBanner: ArrayList<Banner>) {
        this.listBanner.clear()
//        this.listBanner.addAll(listBanner)
//        notifyDataSetChanged()

        listBanner.forEachIndexed { index, banner ->
            this.listBanner.add(banner)
            notifyItemInserted(index)
        }
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Banner) {
            LImageUtil.load(
                context = itemView.imageViewBanner.context,
                any = user.bannerImage,
                imageView = itemView.imageViewBanner
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.i_banner, parent, false)
        )

    override fun getItemCount(): Int = listBanner.size
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listBanner[position])
        }
    }
}
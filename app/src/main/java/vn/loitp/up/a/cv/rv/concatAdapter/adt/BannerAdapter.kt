package vn.loitp.up.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import vn.loitp.databinding.IBannerBinding
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.Banner

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

    inner class DataViewHolder(val binding: IBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Banner) {
            binding.imageViewBanner.loadGlide(
                any = user.bannerImage,
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listBanner.size
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listBanner[position])
        }
    }
}

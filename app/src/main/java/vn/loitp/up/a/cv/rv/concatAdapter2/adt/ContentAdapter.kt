package vn.loitp.up.a.cv.rv.concatAdapter2.adt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.IConcat2DetailBinding
import vn.loitp.up.a.cv.rv.concatAdapter2.model.ContentDetail

@LogTag("ContentAdapter")
class ContentAdapter() : BaseAdapter() {
    private var listContentDetail = ArrayList<ContentDetail>()
    var onClickRootListener: ((ContentDetail, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<ContentDetail>) {
        this.listContentDetail.clear()
        this.listContentDetail.addAll(list)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: IConcat2DetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contentDetail: ContentDetail) {
            binding.tv.text = contentDetail.name
            binding.iv.loadGlide(contentDetail.img)

            binding.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(contentDetail, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            IConcat2DetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listContentDetail.size
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listContentDetail[position])
        }
    }
}

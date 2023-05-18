package vn.loitp.up.a.cv.rv.concatAdapter2.adt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import vn.loitp.databinding.IConcat2TitleBinding
import vn.loitp.up.a.cv.rv.concatAdapter2.model.ContentDetail

@LogTag("TitleAdapter")
class TitleAdapter : BaseAdapter() {

    private var contentDetail: ContentDetail? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(contentDetail: ContentDetail) {
        this.contentDetail = contentDetail
        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: IConcat2TitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(contentDetail: ContentDetail) {
            binding.tv.text = "${contentDetail.parentId} ${contentDetail.parentTitle}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            IConcat2TitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is DataViewHolder) {
            contentDetail?.let {
                holder.bind(it)
            }
        }
    }
}

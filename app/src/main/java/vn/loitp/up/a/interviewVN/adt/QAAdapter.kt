package vn.loitp.up.a.interviewVN.adt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.model.QA
import vn.loitp.databinding.IQaBinding

@LogTag("QAAdapter")
class QAAdapter(
    private val listQA: ArrayList<QA>,
    private val isShowADefault: Boolean,
    private val isShowNextLink: Boolean,
) : BaseAdapter() {

    var onClickRootListener: ((QA, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<QA>) {
        this.listQA.clear()
        this.listQA.addAll(list)
        notifyDataSetChanged()
//        notifyAllViews()
    }

    inner class DataViewHolder(val binding: IQaBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(qa: QA) {
            binding.tvQ.text = "${bindingAdapterPosition + 1} - ${qa.q}"

            if (isShowADefault) {
                if (qa.a.isEmpty()) {
                    binding.tvA.isVisible = false
                } else {
                    binding.tvA.isVisible = true
                    binding.tvA.text = qa.a
                }
            } else {
                binding.tvA.isVisible = false
            }

            if (isShowNextLink) {
                if (qa.nextLink.isEmpty()) {
                    binding.tvNextLink.isVisible = false
                } else {
                    binding.tvNextLink.isVisible = true
                    binding.tvNextLink.text = qa.nextLink
                }
            } else {
                binding.tvNextLink.isVisible = false
            }
            if (qa.ivA.isEmpty()) {
                binding.ivQ.isVisible = false
            } else {
                binding.ivQ.isVisible = true
                binding.ivQ.loadGlide(
                    any = qa.ivQ,
                )
            }

            binding.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(qa, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IQaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listQA.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listQA[position])
        }
    }
}

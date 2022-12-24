package vn.loitp.a.interviewVN.adt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import com.loitp.model.QA
import kotlinx.android.synthetic.main.i_qa.view.*
import vn.loitp.R

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
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(qa: QA) {
            itemView.tvQ.text = "${bindingAdapterPosition + 1} - ${qa.q}"

            if (isShowADefault) {
                if (qa.a.isEmpty()) {
                    itemView.tvA.isVisible = false
                } else {
                    itemView.tvA.isVisible = true
                    itemView.tvA.text = qa.a
                }
            } else {
                itemView.tvA.isVisible = false
            }

            if (isShowNextLink) {
                if (qa.nextLink.isEmpty()) {
                    itemView.tvNextLink.isVisible = false
                } else {
                    itemView.tvNextLink.isVisible = true
                    itemView.tvNextLink.text = qa.nextLink
                }
            } else {
                itemView.tvNextLink.isVisible = false
            }
            if (qa.ivA.isEmpty()) {
                itemView.ivQ.isVisible = false
            } else {
                itemView.ivQ.isVisible = true
                LImageUtil.load(
                    context = itemView.ivQ.context,
                    any = qa.ivQ,
                    imageView = itemView.ivQ
                )
            }

            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(qa, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.i_qa, parent, false
        )
    )

    override fun getItemCount(): Int = listQA.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listQA[position])
        }
    }
}
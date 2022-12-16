package vn.loitp.app.activity.interviewVN.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import com.loitpcore.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_item_qa.view.*
import kotlinx.android.synthetic.main.view_item_qa.view.layoutRoot
import kotlinx.android.synthetic.main.view_item_user.view.*
import vn.loitp.app.R
import com.loitpcore.model.data.QA

@LogTag("QAAdapter")
class QAAdapter(
    private val listQA: ArrayList<QA>
) : BaseAdapter() {

    var onClickRootListener: ((QA, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: ArrayList<QA>) {
        this.listQA.clear()
        this.listQA.addAll(list)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(qa: QA) {
            itemView.tvQ.text = qa.q
            itemView.tvA.text = qa.a
            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(qa, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = DataViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.view_item_qa, parent, false
        )
    )

    override fun getItemCount(): Int = listQA.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listQA[position])
        }
    }
}

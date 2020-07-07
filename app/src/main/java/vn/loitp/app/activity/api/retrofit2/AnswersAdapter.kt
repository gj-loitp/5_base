package vn.loitp.app.activity.api.retrofit2

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by LENOVO on 2/23/2018.
 */
class AnswersAdapter(private val mContext: Context, private var mItems: List<Item>, private val mItemListener: PostItemListener) : RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, postItemListener: PostItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleTv: TextView = itemView.findViewById<View>(R.id.text1) as TextView
        var mItemListener: PostItemListener = postItemListener

        override fun onClick(view: View) {
            val item = getItem(bindingAdapterPosition)
            item.answerId?.toLong()?.let {
                this.mItemListener.onPostClick(it)
            }
            notifyDataSetChanged()
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val postView = inflater.inflate(R.layout.simple_list_item_1, parent, false)
        return ViewHolder(postView, mItemListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]
        val textView = holder.titleTv
        textView.text = item.owner?.displayName
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    fun updateAnswers(items: List<Item>) {
        mItems = items
        notifyDataSetChanged()
    }

    private fun getItem(adapterPosition: Int): Item {
        return mItems[adapterPosition]
    }

    interface PostItemListener {
        fun onPostClick(id: Long)
    }

}

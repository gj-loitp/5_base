package vn.loitp.app.activity.api.retrofit2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.app.R

/**
 * Created by LENOVO on 2/23/2018.
 */
class AnswersAdapter(private var mItems: List<Item>,
                     private val mItemListener: PostItemListener)
    : RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View, postItemListener: PostItemListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleTv: TextView = itemView.findViewById<View>(R.id.text1) as TextView
        private var mItemListener: PostItemListener = postItemListener

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
        val postView = inflater.inflate(R.layout.view_row_test_retrofit, parent, false)
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

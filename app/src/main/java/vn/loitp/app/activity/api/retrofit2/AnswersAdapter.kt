package vn.loitp.app.activity.api.retrofit2

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.app.R
import vn.loitp.app.activity.api.retrofit2.model.Item

class AnswersAdapter(
    private var mItems: List<Item>,
    private val mItemListener: PostItemListener
) : RecyclerView.Adapter<AnswersAdapter.ViewHolder>() {

    inner class ViewHolder(
        itemView: View,
        postItemListener: PostItemListener
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var titleTv: TextView = itemView.findViewById(R.id.text1)
        private var mItemListener: PostItemListener = postItemListener

        @SuppressLint("NotifyDataSetChanged")
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val postView = inflater.inflate(R.layout.view_item_test_retrofit, parent, false)
        return ViewHolder(postView, mItemListener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = mItems[position]
        val textView = holder.titleTv
        textView.text = "displayName: " + item.owner?.displayName
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
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

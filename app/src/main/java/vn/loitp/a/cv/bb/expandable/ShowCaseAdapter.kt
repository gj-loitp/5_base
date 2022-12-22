package vn.loitp.a.cv.bb.expandable

import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R

typealias OnItemClickListener = (info: ShowCaseInfo) -> Unit

class ShowCaseAdapter(
    private val items: List<ShowCaseInfo>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ShowCaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val context = parent.context
        val v = LayoutInflater.from(context).inflate(R.layout.content_showcase_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.isClickable = true
            itemView.isFocusable = true
            itemView.setOnClickListener(this)

            description.movementMethod = LinkMovementMethod.getInstance()
        }

        fun bind(showCaseInfo: ShowCaseInfo) {
            title.text = showCaseInfo.title
            description.text = showCaseInfo.description
        }

        override fun onClick(v: View?) {
            onItemClickListener(items[bindingAdapterPosition])
        }
    }
}

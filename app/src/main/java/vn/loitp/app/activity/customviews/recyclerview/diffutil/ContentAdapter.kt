package vn.loitp.app.activity.customviews.recyclerview.diffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_item_diff_util.view.*
import loitp.basemaster.R
import kotlin.properties.Delegates

class ContentAdapter() : RecyclerView.Adapter<ContentAdapter.ViewHolder>(), AutoUpdatableAdapter {
    val TAG: String = "TAG" + javaClass.simpleName

    var items: List<Content> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_item_diff_util, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(content: Content) = with(itemView) {
            LImageUtil.load(context, content.image, image)
            text.text = content.text
        }
    }
}
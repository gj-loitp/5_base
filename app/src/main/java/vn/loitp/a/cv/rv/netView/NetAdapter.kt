package vn.loitp.a.cv.rv.netView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setTextShadow
import kotlinx.android.synthetic.main.i_net.view.*
import vn.loitp.R

@LogTag("NetAdapter")
class NetAdapter : BaseAdapter() {

    private val listNet = ArrayList<Net>()

    fun addNet(net: Net) {
        this.listNet.add(element = net)
        notifyItemChanged(itemCount - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        this.listNet.clear()
        notifyDataSetChanged()
    }

    var onClickRootView: ((Net) -> Unit)? = null

    inner class NetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(net: Net) {
            itemView.textView.text = net.name
            itemView.textView.setTextShadow(color = Color.BLACK)

            // setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)

            itemView.layoutRootView.setOnClickListener {
                onClickRootView?.invoke(net)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.i_net, parent, false)
        return NetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NetViewHolder) {
            holder.bind(listNet[position])
        }
    }

    override fun getItemCount(): Int {
        return listNet.size
    }
}

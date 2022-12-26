package vn.loitp.app.a.cv.rv.netView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_item_net.view.*
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
//            itemView.rootView.layoutParams.width = sizeW
//            itemView.rootView.layoutParams.height = sizeH
//            itemView.rootView.invalidate()

            itemView.textView.text = net.name
            LUIUtil.setTextShadow(textView = itemView.textView, color = Color.BLACK)

            // setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)

            itemView.layoutRootView.setOnClickListener {
                onClickRootView?.invoke(net)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_net, parent, false)
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

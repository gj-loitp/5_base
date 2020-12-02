package vn.loitp.app.activity.customviews.recyclerview.netview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.utilities.LStoreUtil
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.view_row_item_net.view.*
import vn.loitp.app.R

@LogTag("NetAdapter")
class NetAdapter() : AnimationAdapter() {

    private val listNet = ArrayList<Net>()

//    fun setListNet(listNet: ArrayList<Net>) {
//        this.listNet.clear()
//        this.listNet.addAll(listNet)
//        notifyDataSetChanged()
//    }

    fun addNet(net: Net) {
        this.listNet.add(element = net)
        notifyItemChanged(itemCount - 1)
    }

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

            //setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)

            itemView.layoutRootView.setOnClickListener {
                onClickRootView?.invoke(net)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_net, parent, false)
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

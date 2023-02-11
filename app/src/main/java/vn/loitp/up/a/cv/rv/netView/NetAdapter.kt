package vn.loitp.up.a.cv.rv.netView

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setTextShadow
import vn.loitp.databinding.INetBinding

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

    inner class NetViewHolder(val binding: INetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(net: Net) {
            binding.textView.text = net.name
            binding.textView.setTextShadow(color = Color.BLACK)

            // setAnimation(viewToAnimate = itemView.rootView, position = bindingAdapterPosition)

            binding.layoutRootView.setOnClickListener {
                onClickRootView?.invoke(net)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetViewHolder {
        val binding =
            INetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NetViewHolder(binding)
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

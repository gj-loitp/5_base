package vn.loitp.a.cv.rv.carouselRv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.utilities.LImageUtil
import vn.loitp.R

class DataAdapter(private var list: ArrayList<DataModel>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_carousel, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        LImageUtil.load(
            context = holder.image.context,
            any = list[position].img,
            imageView = holder.image,
        )
    }

    @Suppress("unused")
    fun updateData(list: ArrayList<DataModel>) {
        this.list = list
        notifyItemRangeChanged(0, itemCount)
    }

    @Suppress("unused")
    fun itemChanged() {
        // remove last item for test purposes
        this.list[0] = (DataModel(R.drawable.logo, "Thi is cool"))
        notifyItemChanged(0)

    }

    fun removeData() {
        // remove last item for test purposes
        val orgListSize = list.size
        if (this.list.isNotEmpty()) {
            this.list.removeLast()
            notifyItemRemoved(orgListSize - 1)
        }
    }
}

package vn.loitp.app.activity.customviews.recyclerview.carouselRecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.loitp.app.R

class DataAdapter(private var list: ArrayList<DataModel>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_carousel, parent, false)
        return ViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image).load(list.get(position).img).into(holder.image)
    }

    fun updateData(list: ArrayList<DataModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    //Use the method for item changed
    fun itemChanged() {
        // remove last item for test purposes
        this.list[0] = (DataModel(R.drawable.logo, "Thi is cool"))
        notifyItemChanged(0)

    }

    //Use the method for checking the itemRemoved
    fun removeData() {
        // remove last item for test purposes
        val orgListSize = list.size
        if (this.list.isNotEmpty()) {
            this.list.removeLast()
            notifyItemRemoved(orgListSize - 1)
        }
    }
}
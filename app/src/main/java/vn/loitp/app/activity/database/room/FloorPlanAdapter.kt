package vn.loitp.app.activity.database.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.views.setSafeOnClickListener
import vn.loitp.app.R
import vn.loitp.app.activity.database.room.model.FloorPlan

class FloorPlanAdapter : RecyclerView.Adapter<FloorPlanAdapter.ViewHolder>() {

    private val listFloorPlan = ArrayList<FloorPlan>()

    var onClickRootView: ((FloorPlan) -> Unit)? = null

    fun setListFloorPlan(listFloorPlan: List<FloorPlan>) {
        this.listFloorPlan.clear()
        this.listFloorPlan.addAll(listFloorPlan)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvFloorPlan: TextView = view.findViewById(R.id.tvFloorPlan)
        private val rootView: LinearLayout = view.findViewById(R.id.rootView)

        @SuppressLint("SetTextI18n")
        fun bind(floorPlan: FloorPlan) {
            tvFloorPlan.text = "${floorPlan.id} - ${floorPlan.name}"
            rootView.setSafeOnClickListener {
                onClickRootView?.invoke(floorPlan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_row_item_floor_plan, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFloorPlan[position])
    }

    override fun getItemCount(): Int {
        return listFloorPlan.size
    }
}

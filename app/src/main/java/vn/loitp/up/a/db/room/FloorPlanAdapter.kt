package vn.loitp.up.a.db.room

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.IFloorPlanBinding
import vn.loitp.up.a.db.room.md.FloorPlan

class FloorPlanAdapter : RecyclerView.Adapter<FloorPlanAdapter.ViewHolder>() {

    private val listFloorPlan = ArrayList<FloorPlan>()

    var onClickRootView: ((FloorPlan) -> Unit)? = null
    var onClickUpDate: ((FloorPlan) -> Unit)? = null
    var onClickDelete: ((FloorPlan) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setListFloorPlan(listFloorPlan: List<FloorPlan>) {
        this.listFloorPlan.clear()
        this.listFloorPlan.addAll(listFloorPlan)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: IFloorPlanBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(floorPlan: FloorPlan) {
            binding.tvFloorPlan.text = "${floorPlan.id} - ${floorPlan.name}"
            binding.rootView.setSafeOnClickListener {
                onClickRootView?.invoke(floorPlan)
            }
            binding.ivUpdate.setSafeOnClickListener {
                onClickUpDate?.invoke(floorPlan)
            }
            binding.ivDelete.setSafeOnClickListener {
                onClickDelete?.invoke(floorPlan)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = IFloorPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder, position: Int
    ) {
        holder.bind(listFloorPlan[position])
    }

    override fun getItemCount(): Int {
        return listFloorPlan.size
    }
}

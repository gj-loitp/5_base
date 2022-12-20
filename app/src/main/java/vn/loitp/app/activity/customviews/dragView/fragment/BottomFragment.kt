package vn.loitp.app.activity.customviews.dragView.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.utilities.LImageUtil
import com.tuanhav95.drag.utils.inflate
import kotlinx.android.synthetic.main.frm_drag_view_bottom.*
import kotlinx.android.synthetic.main.item_drag_view_normal.view.*
import vn.loitp.app.R

class BottomFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frm_drag_view_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        recyclerView.adapter = ListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent.inflate(R.layout.item_drag_view_normal))
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            LImageUtil.load(holder.itemView.context, R.drawable.loitp, holder.itemView.ivPhoto)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

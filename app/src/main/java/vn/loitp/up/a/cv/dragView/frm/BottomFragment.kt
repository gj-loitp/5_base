package vn.loitp.up.a.cv.dragView.frm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.loadGlide
import com.tuanhav95.drag.utils.inflate
import vn.loitp.R
import vn.loitp.databinding.FDragViewBottomBinding

class BottomFragment : Fragment() {
    private lateinit var binding: FDragViewBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FDragViewBottomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.recyclerView.adapter = ListAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context)
    }

    class ListAdapter : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(parent.inflate(R.layout.i_drag_view_normal))
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val ivPhoto = holder.itemView.findViewById<AppCompatImageView>(R.id.ivPhoto)
            ivPhoto.loadGlide(
                R.drawable.loitp,
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

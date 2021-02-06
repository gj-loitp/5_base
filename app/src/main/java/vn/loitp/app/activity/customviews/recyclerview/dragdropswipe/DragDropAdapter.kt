package vn.loitp.app.activity.customviews.recyclerview.dragdropswipe

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import vn.loitp.app.R

/**
 * Created by ©Loitp93 on 2/6/2021.
 * VinHMS
 * www.muathu@gmail.com
 */
class DragDropAdapter(dataSet: List<String> = emptyList())
    : DragDropSwipeAdapter<String, DragDropAdapter.ViewHolder>(dataSet) {

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val itemText: TextView = itemView.findViewById(R.id.tv)
        val dragIcon: ImageView = itemView.findViewById(R.id.ivDrag)
    }

    override fun getViewHolder(itemLayout: View) = DragDropAdapter.ViewHolder(itemLayout)

    override fun onBindViewHolder(item: String, viewHolder: DragDropAdapter.ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        viewHolder.itemText.text = item
    }

    override fun getViewToTouchToStartDraggingItem(item: String, viewHolder: DragDropAdapter.ViewHolder, position: Int): View? {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.dragIcon
    }
}

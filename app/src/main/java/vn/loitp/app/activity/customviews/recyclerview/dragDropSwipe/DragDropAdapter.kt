package vn.loitp.app.activity.customviews.recyclerview.dragDropSwipe

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeAdapter
import com.loitp.core.utilities.LScreenUtil
import com.loitp.core.utilities.LUIUtil
import vn.loitp.R

class DragDropAdapter(
    dataSet: List<String> = emptyList(),
    private val isHorizontal: Boolean = false
) : DragDropSwipeAdapter<String, DragDropAdapter.ViewHolder>(dataSet) {

    private val sizeWidth = LScreenUtil.screenWidth / 2

    class ViewHolder(itemView: View) : DragDropSwipeAdapter.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.tv)
        val ivDrag: ImageView = itemView.findViewById(R.id.ivDrag)
    }

    override fun getViewHolder(itemView: View) = ViewHolder(itemView)

    override fun onBindViewHolder(item: String, viewHolder: ViewHolder, position: Int) {
        // Here we update the contents of the view holder's views to reflect the item's data
        if (isHorizontal) {
            LUIUtil.setSizeOfView(view = viewHolder.itemView, width = sizeWidth, height = null)
        }
        viewHolder.tv.text = item
    }

    override fun getViewToTouchToStartDraggingItem(
        item: String,
        viewHolder: ViewHolder,
        position: Int
    ): View {
        // We return the view holder's view on which the user has to touch to drag the item
        return viewHolder.ivDrag
    }
}

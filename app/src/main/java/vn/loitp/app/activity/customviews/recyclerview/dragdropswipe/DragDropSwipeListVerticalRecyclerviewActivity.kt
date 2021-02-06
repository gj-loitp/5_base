package vn.loitp.app.activity.customviews.recyclerview.dragdropswipe

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.activity_recycler_drag_drop_swipe_list_vertical.*
import vn.loitp.app.R

//https://github.com/ernestoyaquello/DragDropSwipeRecyclerview
@LogTag("DragDropSwipeRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeListVerticalRecyclerviewActivity : BaseFontActivity() {

    private var dragDropAdapter: DragDropAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_drag_drop_swipe_list_vertical
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val dataSet = ArrayList<String>()
        for (i in 0..20) {
            dataSet.add(element = "Item $i")
        }
        dragDropAdapter = DragDropAdapter(dataSet)
        dragDropSwipeRecyclerView.layoutManager = LinearLayoutManager(this)//list
        dragDropSwipeRecyclerView.adapter = dragDropAdapter

        val isRestrictingDraggingDirections = false
        if (isRestrictingDraggingDirections) {
            dragDropSwipeRecyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        } else {
            dragDropSwipeRecyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_UNCONSTRAINED_DRAGGING
        }

        val isDetailLayout = true
        if (isDetailLayout) {
            dragDropSwipeRecyclerView.itemLayoutId = R.layout.view_row_item_drag_drop_recycler_detail_view
            dragDropSwipeRecyclerView.dividerDrawableId = null
        } else {
            dragDropSwipeRecyclerView.itemLayoutId = R.layout.view_row_item_drag_drop_recycler_view
            dragDropSwipeRecyclerView.dividerDrawableId = R.drawable.list_divider
        }
    }
}

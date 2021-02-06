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
@LogTag("DragDropSwipeListVerticalRecyclerviewActivity")
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

    private fun setData(): ArrayList<String> {
        val dataSet = ArrayList<String>()
        for (i in 0..20) {
            dataSet.add(element = "Item $i")
        }
        return dataSet
    }

    private fun setupViews() {
        dragDropAdapter = DragDropAdapter(setData())

        dragDropSwipeRecyclerView.layoutManager = LinearLayoutManager(this)//list
        dragDropSwipeRecyclerView.adapter = dragDropAdapter

        setIsRestrictingDraggingDirections(isRestrictingDraggingDirections = false)

        swIsRestrictingDraggingDirections.setOnCheckedChangeListener { _, isChecked ->
            setIsRestrictingDraggingDirections(isRestrictingDraggingDirections = isChecked)
        }
    }

    private fun setIsRestrictingDraggingDirections(isRestrictingDraggingDirections: Boolean) {
        if (isRestrictingDraggingDirections) {
            dragDropSwipeRecyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING
        } else {
            dragDropSwipeRecyclerView.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_UNCONSTRAINED_DRAGGING
        }
    }

}

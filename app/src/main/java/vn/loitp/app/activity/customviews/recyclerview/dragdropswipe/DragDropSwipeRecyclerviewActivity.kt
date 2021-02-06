package vn.loitp.app.activity.customviews.recyclerview.dragdropswipe

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import kotlinx.android.synthetic.main.activity_recycler_drag_drop_swipe.*
import vn.loitp.app.R

//https://github.com/ernestoyaquello/DragDropSwipeRecyclerview
@LogTag("DragDropSwipeRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeRecyclerviewActivity : BaseFontActivity() {

    private var dragDropAdapter: DragDropAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_drag_drop_swipe
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
        dragDropSwipeRecyclerView.layoutManager = LinearLayoutManager(this)
        dragDropSwipeRecyclerView.adapter = dragDropAdapter

        //Set up the orientation
//        dragDropSwipeRecyclerView?.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING

        // This disallows swiping items to the right
//        dragDropSwipeRecyclerView?.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)
//        dragDropSwipeRecyclerView?.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.LEFT)
    }
}

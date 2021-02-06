package vn.loitp.app.activity.customviews.recyclerview.dragdropswipe

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import vn.loitp.app.R

//https://github.com/ernestoyaquello/DragDropSwipeRecyclerview
@LogTag("DragDropSwipeRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeRecyclerviewActivity : BaseFontActivity() {

    private var mAdapter: MyAdapter? = null
    private var mList: DragDropSwipeRecyclerView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_drag_drop_swipe
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSet = ArrayList<String>()
        for (i in 0..20) {
            dataSet.add(element = "Item $i")
        }
        mAdapter = MyAdapter(dataSet)
        mList = findViewById(R.id.list)
        mList?.layoutManager = LinearLayoutManager(this)
        mList?.adapter = mAdapter

        //Set up the orientation
        mList?.orientation = DragDropSwipeRecyclerView.ListOrientation.VERTICAL_LIST_WITH_VERTICAL_DRAGGING

        // This disallows swiping items to the right
        mList?.disableSwipeDirection(DragDropSwipeRecyclerView.ListOrientation.DirectionFlag.RIGHT)

    }
}

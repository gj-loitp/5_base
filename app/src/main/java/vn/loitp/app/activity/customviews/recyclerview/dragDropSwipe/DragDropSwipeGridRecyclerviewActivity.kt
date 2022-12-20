package vn.loitp.app.activity.customviews.recyclerview.dragDropSwipe

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_drag_drop_swipe_grid.*
import vn.loitp.app.R

@LogTag("DragDropSwipeGridRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeGridRecyclerviewActivity : BaseFontActivity() {

    private var dragDropAdapter: DragDropAdapter? = null
    private val numberOfColumns = 2

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_drag_drop_swipe_grid
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
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/ernestoyaquello/DragDropSwipeRecyclerview"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DragDropSwipeGridRecyclerviewActivity::class.java.simpleName
        }

        dragDropAdapter = DragDropAdapter(setData())

        dragDropSwipeRecyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        dragDropSwipeRecyclerView.adapter = dragDropAdapter

        setIsRestrictingDraggingDirections()
        setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = false)

        swLayoutBehind.setOnCheckedChangeListener { _, isChecked ->
            setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = isChecked)
        }

        // listener -> check DragDropSwipeListHorizontalRecyclerviewActivity
    }

    private fun setIsRestrictingDraggingDirections() {
        dragDropSwipeRecyclerView.orientation =
            DragDropSwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING
        dragDropSwipeRecyclerView.numOfColumnsPerRowInGridList = numberOfColumns
    }

    private fun setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems: Boolean) {
        // We set to null all the properties that can be used to display something behind swiped items
        // In XML: app:behind_swiped_item_bg_color="@null"
        dragDropSwipeRecyclerView.behindSwipedItemBackgroundColor = null

        // In XML: app:behind_swiped_item_bg_color_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemBackgroundSecondaryColor = null

        // In XML: app:behind_swiped_item_icon="@null"
        dragDropSwipeRecyclerView.behindSwipedItemIconDrawableId = null

        // In XML: app:behind_swiped_item_icon_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemIconSecondaryDrawableId = null

        // In XML: app:behind_swiped_item_custom_layout="@null"
        dragDropSwipeRecyclerView.behindSwipedItemLayoutId = null

        // In XML: app:behind_swiped_item_custom_layout_secondary="@null"
        dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId = null

        if (isDrawingBehindSwipedItems) {
            // We set our custom layouts to be displayed behind swiped items
            // In XML: app:behind_swiped_item_custom_layout="@layout/behind_swiped_vertical_list"
            dragDropSwipeRecyclerView.behindSwipedItemLayoutId =
                R.layout.layout_behind_swiped_vertical_list

            // In XML: app:behind_swiped_item_custom_layout_secondary="@layout/behind_swiped_vertical_list_secondary"
            dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId =
                R.layout.layout_behind_swiped_vertical_list_secondary
        }
    }
}

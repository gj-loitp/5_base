package vn.loitp.up.a.cv.rv.dragDropSwipe

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemDragListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnItemSwipeListener
import com.ernestoyaquello.dragdropswiperecyclerview.listener.OnListScrollListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvDragDropSwipeListHorizontalBinding

@LogTag("DragDropSwipeListHorizontalRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeListHorizontalRecyclerviewActivity : BaseActivityFont() {
    private lateinit var binding: ARvDragDropSwipeListHorizontalBinding
    private var dragDropAdapter: DragDropAdapter? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvDragDropSwipeListHorizontalBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/ernestoyaquello/DragDropSwipeRecyclerview"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text =
                DragDropSwipeListHorizontalRecyclerviewActivity::class.java.simpleName
        }

        dragDropAdapter = DragDropAdapter(dataSet = setData(), isHorizontal = true)

        binding.dragDropSwipeRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.dragDropSwipeRecyclerView.adapter = dragDropAdapter

        binding.dragDropSwipeRecyclerView.swipeListener = onItemSwipeListener
        binding.dragDropSwipeRecyclerView.dragListener = onItemDragListener
        binding.dragDropSwipeRecyclerView.scrollListener = onListScrollListener

        setIsRestrictingDraggingDirections(isRestrictingDraggingDirections = false)
        setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = false)

        binding.swIsRestrictingDraggingDirections.setOnCheckedChangeListener { _, isChecked ->
            setIsRestrictingDraggingDirections(isRestrictingDraggingDirections = isChecked)
        }
        binding.swLayoutBehind.setOnCheckedChangeListener { _, isChecked ->
            setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = isChecked)
        }
    }

    private fun setIsRestrictingDraggingDirections(isRestrictingDraggingDirections: Boolean) {
        if (isRestrictingDraggingDirections) {
            binding.dragDropSwipeRecyclerView.orientation =
                DragDropSwipeRecyclerView.ListOrientation.HORIZONTAL_LIST_WITH_HORIZONTAL_DRAGGING
        } else {
            binding.dragDropSwipeRecyclerView.orientation =
                DragDropSwipeRecyclerView.ListOrientation.HORIZONTAL_LIST_WITH_UNCONSTRAINED_DRAGGING
        }
    }

    private fun setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems: Boolean) {
        // We set to null all the properties that can be used to display something behind swiped items
        // In XML: app:behind_swiped_item_bg_color="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemBackgroundColor = null

        // In XML: app:behind_swiped_item_bg_color_secondary="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemBackgroundSecondaryColor = null

        // In XML: app:behind_swiped_item_icon="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemIconDrawableId = null

        // In XML: app:behind_swiped_item_icon_secondary="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemIconSecondaryDrawableId = null

        // In XML: app:behind_swiped_item_custom_layout="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemLayoutId = null

        // In XML: app:behind_swiped_item_custom_layout_secondary="@null"
        binding.dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId = null

        if (isDrawingBehindSwipedItems) {
            // We set our custom layouts to be displayed behind swiped items
            // In XML: app:behind_swiped_item_custom_layout="@layout/behind_swiped_vertical_list"
            binding.dragDropSwipeRecyclerView.behindSwipedItemLayoutId =
                R.layout.layout_behind_swiped_vertical_list

            // In XML: app:behind_swiped_item_custom_layout_secondary="@layout/behind_swiped_vertical_list_secondary"
            binding.dragDropSwipeRecyclerView.behindSwipedItemSecondaryLayoutId =
                R.layout.layout_behind_swiped_vertical_list_secondary
        }
    }

    private val onItemSwipeListener = object : OnItemSwipeListener<String> {
        override fun onItemSwiped(
            position: Int,
            direction: OnItemSwipeListener.SwipeDirection,
            item: String
        ): Boolean {
            when (direction) {
                OnItemSwipeListener.SwipeDirection.RIGHT_TO_LEFT -> logD("onItemSwipeListener RIGHT_TO_LEFT")
                OnItemSwipeListener.SwipeDirection.LEFT_TO_RIGHT -> logD("onItemSwipeListener LEFT_TO_RIGHT")
                OnItemSwipeListener.SwipeDirection.DOWN_TO_UP -> logD("onItemSwipeListener DOWN_TO_UP")
                OnItemSwipeListener.SwipeDirection.UP_TO_DOWN -> logD("onItemSwipeListener UP_TO_DOWN")
            }
            return false
        }
    }

    private val onItemDragListener = object : OnItemDragListener<String> {
        override fun onItemDragged(previousPosition: Int, newPosition: Int, item: String) {
            // Handle action of item being dragged from one position to another
            logD("onItemDragListener onItemDragged previousPosition $previousPosition, newPosition $newPosition, item $item")
        }

        override fun onItemDropped(initialPosition: Int, finalPosition: Int, item: String) {
            // Handle action of item dropped
            logD("onItemDragListener onItemDragged initialPosition $initialPosition, finalPosition $finalPosition, item $item")
        }
    }

    private val onListScrollListener = object : OnListScrollListener {
        override fun onListScrollStateChanged(scrollState: OnListScrollListener.ScrollState) {
            // Handle change on list scroll state
            logD("onListScrollListener onListScrollStateChanged scrollState $scrollState")
        }

        override fun onListScrolled(
            scrollDirection: OnListScrollListener.ScrollDirection,
            distance: Int
        ) {
            // Handle scrolling
            logD("onListScrollListener onListScrolled scrollDirection $scrollDirection, distance $distance")
        }
    }
}

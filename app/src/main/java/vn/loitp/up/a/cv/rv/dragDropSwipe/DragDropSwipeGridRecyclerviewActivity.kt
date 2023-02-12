package vn.loitp.up.a.cv.rv.dragDropSwipe

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.ernestoyaquello.dragdropswiperecyclerview.DragDropSwipeRecyclerView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvDragDropSwipeGridBinding

@LogTag("DragDropSwipeGridRecyclerviewActivity")
@IsFullScreen(false)
class DragDropSwipeGridRecyclerviewActivity : BaseActivityFont() {
    private lateinit var binding: ARvDragDropSwipeGridBinding

    private var dragDropAdapter: DragDropAdapter? = null
    private val numberOfColumns = 2

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvDragDropSwipeGridBinding.inflate(layoutInflater)
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
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DragDropSwipeGridRecyclerviewActivity::class.java.simpleName
        }

        dragDropAdapter = DragDropAdapter(setData())

        binding.dragDropSwipeRecyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        binding.dragDropSwipeRecyclerView.adapter = dragDropAdapter

        setIsRestrictingDraggingDirections()
        setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = false)

        binding.swLayoutBehind.setOnCheckedChangeListener { _, isChecked ->
            setupLayoutBehindItemLayoutOnSwiping(isDrawingBehindSwipedItems = isChecked)
        }

        // listener -> check DragDropSwipeListHorizontalRecyclerviewActivity
    }

    private fun setIsRestrictingDraggingDirections() {
        binding.dragDropSwipeRecyclerView.orientation =
            DragDropSwipeRecyclerView.ListOrientation.GRID_LIST_WITH_HORIZONTAL_SWIPING
        binding.dragDropSwipeRecyclerView.numOfColumnsPerRowInGridList = numberOfColumns
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
}

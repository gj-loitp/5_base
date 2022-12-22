package vn.loitp.app.activity.customviews.recyclerview.fastScroll

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R
import vn.loitp.app.activity.customviews.recyclerview.fastScroll.db.ListItem

class HeaderSpacerItemDecoration(
    val getListItem: (Int) -> ListItem
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val listItem = getListItem(position)
        outRect.apply {
            when (listItem) {
                is ListItem.HeaderItem -> {
                    top = if (position == 0) {
                        0
                    } else {
                        view.context.resources.getDimensionPixelSize(R.dimen.header_middle_top_margin)
                    }
                }
                else -> {
                    // do nothing
                }
            }
            left = 0
            right = 0
            bottom = 0
        }
    }
}

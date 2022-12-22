package vn.loitp.app.a.customviews.recyclerview.fastScroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView
import kotlinx.android.synthetic.main.layout_fast_scroll_sample_basic.*
import vn.loitp.R
import vn.loitp.app.a.customviews.recyclerview.fastScroll.adapter.SampleAdapter
import vn.loitp.app.a.customviews.recyclerview.fastScroll.db.ListItem
import vn.loitp.app.a.customviews.recyclerview.fastScroll.db.SAMPLE_DATA_TEXT
import java.util.*

class CustomScrollFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.layout_fast_scroll_sample_basic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val data = listOf(
            ListItem.DataItem(
                "Items will be scrolled to the top!",
                showInFastScroll = false
            )
        ) + SAMPLE_DATA_TEXT

        val linearLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = SampleAdapter(data)
        }

        fastScrollerView.apply {
            setupWithRecyclerView(
                recyclerView,
                { position ->
                    data[position]
                        .takeIf(ListItem::showInFastScroll)
                        ?.let { item ->
                            FastScrollItemIndicator.Text(
                                item
                                    .title
                                    .substring(0, 1)
                                    .uppercase(Locale.getDefault())
                            )
                        }
                },
                useDefaultScroller = false
            )
            val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            }
            itemIndicatorSelectedCallbacks += object :
                FastScrollerView.ItemIndicatorSelectedCallback {
                override fun onItemIndicatorSelected(
                    indicator: FastScrollItemIndicator,
                    indicatorCenterY: Int,
                    itemPosition: Int
                ) {
                    recyclerView.stopScroll()
                    smoothScroller.targetPosition = itemPosition
                    linearLayoutManager.startSmoothScroll(smoothScroller)
                }
            }
        }

        fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = fastScrollerView)
        }
    }
}

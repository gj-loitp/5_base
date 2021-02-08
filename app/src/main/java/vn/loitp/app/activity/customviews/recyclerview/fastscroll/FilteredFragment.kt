package vn.loitp.app.activity.customviews.recyclerview.fastscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerThumbView
import com.reddit.indicatorfastscroll.FastScrollerView
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.adapter.SampleAdapter
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.db.ListItem
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.db.SAMPLE_DATA_TEXT

class FilteredFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fastScrollerView: FastScrollerView
    private lateinit var fastScrollerThumbView: FastScrollerThumbView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.sample_basic, container, false)

        val data = listOf(ListItem.DataItem(
                "Every other indicator will be hidden!",
                showInFastScroll = false
        )) + SAMPLE_DATA_TEXT

        recyclerView = view.findViewById(R.id.sample_basic_recyclerview)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SampleAdapter(data)
        }

        fastScrollerView = view.findViewById(R.id.sample_basic_fastscroller)
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
                                                .toUpperCase()
                                )
                              }
                    },
                    showIndicator = { indicator, indicatorPosition, totalIndicators ->
                      // Hide every other indicator
                      indicatorPosition % 2 == 0
                    }
            )
        }

        fastScrollerThumbView = view.findViewById(R.id.sample_basic_fastscroller_thumb)
        fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView)
        }

        return view
    }

}

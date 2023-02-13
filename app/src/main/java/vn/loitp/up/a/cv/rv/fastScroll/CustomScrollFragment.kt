package vn.loitp.up.a.cv.rv.fastScroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import com.reddit.indicatorfastscroll.FastScrollerView
import vn.loitp.databinding.FFastScrollSampleBasicBinding
import vn.loitp.up.a.cv.rv.fastScroll.adt.SampleAdapter
import vn.loitp.up.a.cv.rv.fastScroll.db.ListItem
import vn.loitp.up.a.cv.rv.fastScroll.db.SAMPLE_DATA_TEXT
import java.util.*

class CustomScrollFragment : Fragment() {
    private lateinit var binding: FFastScrollSampleBasicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FFastScrollSampleBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = SampleAdapter(data)
        }

        binding.fastScrollerView.apply {
            setupWithRecyclerView(
                binding.recyclerView,
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
                    binding.recyclerView.stopScroll()
                    smoothScroller.targetPosition = itemPosition
                    linearLayoutManager.startSmoothScroll(smoothScroller)
                }
            }
        }

        binding.fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = binding.fastScrollerView)
        }
    }
}

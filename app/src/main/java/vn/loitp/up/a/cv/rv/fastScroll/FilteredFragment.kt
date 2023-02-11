package vn.loitp.up.a.cv.rv.fastScroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import vn.loitp.databinding.FFastScrollSampleBasicBinding
import vn.loitp.up.a.cv.rv.fastScroll.adt.SampleAdapter
import vn.loitp.up.a.cv.rv.fastScroll.db.ListItem
import vn.loitp.up.a.cv.rv.fastScroll.db.SAMPLE_DATA_TEXT
import java.util.*

class FilteredFragment : Fragment() {
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
                "Every other indicator will be hidden!",
                showInFastScroll = false
            )
        ) + SAMPLE_DATA_TEXT

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
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
                showIndicator = { _, indicatorPosition, _ ->
                    // Hide every other indicator
                    indicatorPosition % 2 == 0
                }
            )
        }

        binding.fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = binding.fastScrollerView)
        }
    }
}

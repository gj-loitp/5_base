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
import vn.loitp.up.a.cv.rv.fastScroll.db.SAMPLE_DATA_TEXT_AND_HEADERS
import java.util.*

class TextWithIconFragment : Fragment() {

    private lateinit var binding: FFastScrollSampleBasicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FFastScrollSampleBasicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val data = SAMPLE_DATA_TEXT_AND_HEADERS

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(HeaderSpacerItemDecoration(data::get))
            adapter = SampleAdapter(data)
        }

        binding.fastScrollerView.apply {
            setupWithRecyclerView(binding.recyclerView, { position ->
                data[position].takeIf(ListItem::showInFastScroll)?.let { item ->
                    when (item) {
                        is ListItem.HeaderItem -> FastScrollItemIndicator.Icon(item.iconRes)
                        is ListItem.DataItem -> FastScrollItemIndicator.Text(
                            item.title.substring(0, 1).uppercase(Locale.getDefault())
                        )
                    }
                }
            })
        }

        binding.fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = binding.fastScrollerView)
        }
    }
}

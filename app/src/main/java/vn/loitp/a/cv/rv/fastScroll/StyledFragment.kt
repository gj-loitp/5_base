package vn.loitp.a.cv.rv.fastScroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.reddit.indicatorfastscroll.FastScrollItemIndicator
import kotlinx.android.synthetic.main.f_fast_scroll_sample_styled.*
import vn.loitp.R
import vn.loitp.a.cv.rv.fastScroll.adt.SampleAdapter
import vn.loitp.a.cv.rv.fastScroll.db.ListItem
import vn.loitp.a.cv.rv.fastScroll.db.SAMPLE_DATA_TEXT
import java.util.*

class StyledFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.f_fast_scroll_sample_styled, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        val data = SAMPLE_DATA_TEXT

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
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
                }
            )
        }

        fastScrollerThumbView.apply {
            setupWithFastScroller(fastScrollerView = fastScrollerView)
        }
    }
}

package vn.loitp.up.a.cv.progress.segmentedPb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import pt.tornelas.segmentedprogressbar.SegmentedProgressBarListener
import vn.loitp.databinding.AStandardExampleBinding
import vn.loitp.up.a.cv.progress.segmentedPb.pager.PagerAdapter

@LogTag("StandardExampleActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class StandardExampleActivity : BaseActivityFont() {
    private lateinit var binding: AStandardExampleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AStandardExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = dataSource()

        binding.layoutPager.viewPager.adapter = PagerAdapter(supportFragmentManager, items)
        binding.spb.viewPager = binding.layoutPager.viewPager

        binding.spb.segmentCount = items.size
        binding.spb.listener = object : SegmentedProgressBarListener {
            override fun onPage(oldPageIndex: Int, newPageIndex: Int) {
                // New page started animating
            }

            override fun onFinished() {
                finish()
            }
        }

        binding.spb.start()

        binding.layoutPager.btnNext.setOnClickListener { binding.spb.next() }
        binding.layoutPager.btnPrevious.setOnClickListener { binding.spb.previous() }
    }
}

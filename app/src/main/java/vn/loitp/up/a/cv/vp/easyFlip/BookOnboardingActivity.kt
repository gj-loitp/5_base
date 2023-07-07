package vn.loitp.up.a.cv.vp.easyFlip

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import vn.loitp.R
import vn.loitp.databinding.ABookOnboardingBinding

@LogTag("BookOnboardingActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class BookOnboardingActivity : BaseActivityFont() {
    private lateinit var binding: ABookOnboardingBinding
    private var mPagerAdapter: PagerAdapter? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ABookOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPagerAdapter = BookOnboardingPagerAdapter(supportFragmentManager)
        binding.pager.adapter = mPagerAdapter
        binding.pager.clipToPadding = true
        binding.pager.setPageTransformer(true, BookFlipPageTransformer())
    }

    class BookOnboardingPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var fragments = arrayListOf<BookPageIntroFragment>()

        init {
            val titles =
                arrayOf("All About Reading", "Find Your Love", "Pick Your Books", "Enjoy Your Time")
            val subtitles = arrayOf(
                "Everyone love reading books",
                "All books in your library",
                "Books are your best friends",
                "All set and get started now"
            )
            val imageIds = intArrayOf(
                R.drawable.all_about_reading,
                R.drawable.find_your_love,
                R.drawable.pick_your_books,
                R.drawable.enjoy_your_time
            )
            for (i in 0..3) {
                val frag = BookPageIntroFragment.newInstance(titles[i], subtitles[i], imageIds[i])
                fragments.add(frag)
            }
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }
    }
}

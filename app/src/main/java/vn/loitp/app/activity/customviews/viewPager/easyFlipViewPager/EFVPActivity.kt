package vn.loitp.app.activity.customviews.viewPager.easyFlipViewPager

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import com.wajahatkarim3.easyflipviewpager.CardFlipPageTransformer
import kotlinx.android.synthetic.main.activity_efvp.*
import vn.loitp.app.R

@LogTag("EFVPActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class EFVPActivity : BaseFontActivity() {
    private lateinit var mPager: ViewPager
    private var mPagerAdapter: PagerAdapter? = null

    private var bookFlipTransformer = BookFlipPageTransformer()
    var cardFlipTransformer = CardFlipPageTransformer()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_efvp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/wajahatkarim3/EasyFlipViewPager"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EFVPActivity::class.java.simpleName
        }

        // Book Flip Transformer
        bookFlipTransformer.isEnableScale = true
        bookFlipTransformer.scaleAmountPercent = 10f

        // Card Flip Transformer
        cardFlipTransformer.isScalable = false
        cardFlipTransformer.flipOrientation = CardFlipPageTransformer.VERTICAL

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager)
        mPagerAdapter = DemoPagerAdapter(supportFragmentManager)
        mPager.adapter = mPagerAdapter
        mPager.clipToPadding = false

        updatePagerConfigs()

        rgFlipAnimation.setOnCheckedChangeListener { _, _ ->
            updatePagerConfigs()
        }

        checkEnableScale.setOnCheckedChangeListener { _, _ ->
            updatePagerConfigs()
        }
    }

    private fun updatePagerConfigs() {
        when (rgFlipAnimation.checkedRadioButtonId) {
            R.id.radioBookFlip -> {
                bookFlipTransformer.isEnableScale = checkEnableScale.isChecked
                mPager.setPageTransformer(true, bookFlipTransformer)
            }
            R.id.radioCardFlip -> {
                cardFlipTransformer.isScalable = checkEnableScale.isChecked
                mPager.setPageTransformer(true, cardFlipTransformer)
            }
        }
    }

    class DemoPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        var fragmentsList = arrayListOf<GalleryImageFragment>()

        init {
            val titles = arrayOf(
                "Book Onboarding",
                "Poker Card",
                "Pakistan Gallery",
                "ViewPager2 Demo"
            )
            val imageIds = intArrayOf(
                R.drawable.books_snap,
                R.drawable.poker_snap,
                R.drawable.gallery_snap,
                R.drawable.viewpager2_snap
            )
            for (i in imageIds.indices) {
                val frag = GalleryImageFragment.newInstance(titles[i], null, imageIds[i])
                fragmentsList.add(frag)
            }
        }

        override fun getItem(position: Int): Fragment = fragmentsList[position]

        override fun getCount() = fragmentsList.size

    }
}

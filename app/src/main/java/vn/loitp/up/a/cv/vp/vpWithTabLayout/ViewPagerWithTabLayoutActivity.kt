package vn.loitp.up.a.cv.vp.vpWithTabLayout

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.getRandomColor
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showDialogList
import com.loitp.views.vp.vpTransformers.*
import vn.loitp.R
import vn.loitp.databinding.AVpWithTablayoutBinding

// https://github.com/geftimov/android-viewpager-transformers
@LogTag("ViewPagerWithTabLayoutActivity")
@IsFullScreen(false)
class ViewPagerWithTabLayoutActivity : BaseActivityFont() {
    private val resList: MutableList<Int> = ArrayList()

    companion object {
        private const val AccordionTransformer = "AccordionTransformer"
        private const val BackgroundToForegroundTransformer = "BackgroundToForegroundTransformer"
        private const val CubeInTransformer = "CubeInTransformer"
        private const val CubeOutTransformer = "CubeOutTransformer"
        private const val DefaultTransformer = "DefaultTransformer"
        private const val DepthPageTransformer = "DepthPageTransformer"
        private const val DrawFromBackTransformer = "DrawFromBackTransformer"
        private const val FlipHorizontalTransformer = "FlipHorizontalTransformer"
        private const val FlipVerticalTransformer = "FlipVerticalTransformer"
        private const val ForegroundToBackgroundTransformer = "ForegroundToBackgroundTransformer"

        @Suppress("unused")
        private const val ParallaxPageTransformer = "ParallaxPageTransformer"
        private const val RotateUpTransformer = "RotateUpTransformer"
        private const val RotateDownTransformer = "RotateDownTransformer"
        private const val StackTransformer = "StackTransformer"
        private const val TabletTransformer = "TabletTransformer"
        private const val ZoomInTransformer = "ZoomInTransformer"
        private const val ZoomOutSlideTransformer = "ZoomOutSlideTransformer"
        private const val ZoomOutTranformer = "ZoomOutTranformer"
    }

    private lateinit var binding: AVpWithTablayoutBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpWithTablayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerWithTabLayoutActivity::class.java.simpleName
        }
        for (i in 0..19) {
            resList.add(getRandomColor())
        }
        binding.viewPager.adapter = SlidePagerAdapter()
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.changeTabsFont(FONT_PATH)
        binding.btAnim.setOnClickListener {
            showDialogAnim()
        }
    }

    private fun showDialogAnim() {
        val stringList: MutableList<String?> = ArrayList()
        stringList.add(AccordionTransformer)
        stringList.add(BackgroundToForegroundTransformer)
        stringList.add(CubeInTransformer)
        stringList.add(CubeOutTransformer)
        stringList.add(DefaultTransformer)
        stringList.add(DepthPageTransformer)
        stringList.add(DrawFromBackTransformer)
        stringList.add(FlipHorizontalTransformer)
        stringList.add(FlipVerticalTransformer)
        stringList.add(ForegroundToBackgroundTransformer)
        // stringList.add(ParallaxPageTransformer);
        stringList.add(RotateDownTransformer)
        stringList.add(RotateUpTransformer)
        stringList.add(StackTransformer)
        stringList.add(TabletTransformer)
        stringList.add(ZoomInTransformer)
        stringList.add(ZoomOutSlideTransformer)
        stringList.add(ZoomOutTranformer)
        val arr = stringList.toTypedArray()

        this.showDialogList(
            title = "Select",
            arr = arr,
            onClick = { position ->
                showShortInformation("Click position " + position + ", item: " + arr[position])
                when (stringList[position]) {
                    AccordionTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        AccordionTransformer()
                    )
                    BackgroundToForegroundTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        BackgroundToForegroundTransformer()
                    )
                    CubeInTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        CubeInTransformer()
                    )
                    CubeOutTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        CubeOutTransformer()
                    )
                    DefaultTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        DefaultTransformer()
                    )
                    DepthPageTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        DepthPageTransformer()
                    )
                    DrawFromBackTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        DrawFromBackTransformer()
                    )
                    FlipHorizontalTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        FlipHorizontalTransformer()
                    )
                    FlipVerticalTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        FlipVerticalTransformer()
                    )
                    ForegroundToBackgroundTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        ForegroundToBackgroundTransformer()
                    )
                    RotateDownTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        RotateDownTransformer()
                    )
                    RotateUpTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        RotateUpTransformer()
                    )
                    StackTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        StackTransformer()
                    )
                    TabletTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        TabletTransformer()
                    )
                    ZoomInTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        ZoomInTransformer()
                    )
                    ZoomOutSlideTransformer -> binding.viewPager.setPageTransformer(
                        true,
                        ZoomOutSlideTransformer()
                    )
                    ZoomOutTranformer -> binding.viewPager.setPageTransformer(
                        true,
                        ZoomOutTransformer()
                    )
                }
            }
        )
    }

    private inner class SlidePagerAdapter : PagerAdapter() {
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
//            val res = resList[position]
            val inflater = LayoutInflater.from(this@ViewPagerWithTabLayoutActivity)
            val layout =
                inflater.inflate(R.layout.item_photo_slide_iv, collection, false) as ViewGroup
            val imageView = layout.findViewById<ImageView>(R.id.imageView)
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv)
            } else {
                imageView.setImageResource(R.drawable.logo)
            }
            val tv = layout.findViewById<TextView>(R.id.textView)
            tv.text = position.toString() + "/" + resList.size
            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            if (view is View) {
                collection.removeView(view)
            }
        }

        override fun getCount(): Int {
            return resList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence {
            return "Page Title $position"
        }
    }
}

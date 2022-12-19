package vn.loitp.app.activity.animation.lottie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_lottie.*
import vn.loitp.app.R
import java.io.IOException

// https://www.lottiefiles.com/?page=1
@LogTag("MenuLottieActivity")
@IsFullScreen(false)
class MenuLottieActivity : BaseFontActivity() {
    private val lottieItemList: MutableList<LottieItem> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_lottie
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        prepareData()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuLottieActivity::class.java.simpleName
        }

        val slidePagerAdapter = SlidePagerAdapter()
        viewPager.adapter = slidePagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        LUIUtil.changeTabsFont(tabLayout = tabLayout, fontName = Constants.FONT_PATH)

        sb.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewPager.currentItem = seekBar.progress
            }
        })
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                sb.progress = i
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }

    private fun prepareData() {
        getListAssetFiles("lottie")
        for (i in stringList.indices) {
            lottieItemList.add(
                LottieItem(
                    i.toString() + " - " + stringList[i],
                    "lottie/" + stringList[i]
                )
            )
        }
        sb.max = lottieItemList.size
        viewPager.adapter?.notifyDataSetChanged()
    }

    private val stringList: MutableList<String> = ArrayList()
    private fun getListAssetFiles(path: String): Boolean {
        val list: Array<String>?
        try {
            list = assets.list(path)
            if (list?.isNotEmpty() == true) {
                // This is a folder
                for (file in list) {
                    if (!getListAssetFiles(path = "$path/$file")) return false else {
                        // This is a file
                        stringList.add(file)
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private inner class SlidePagerAdapter : PagerAdapter() {
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val lottieItem = lottieItemList[position]
            val inflater = LayoutInflater.from(this@MenuLottieActivity)
            val layout = inflater.inflate(R.layout.item_lottie_view, collection, false) as ViewGroup
            val rl = layout.findViewById<ViewGroup>(R.id.rl)
            val animationView: LottieAnimationView = layout.findViewById(R.id.animationView)
            animationView.setAnimation(lottieItem.pathAsset)
            // lottieAnimationView.useHardwareAcceleration();
            // lottieAnimationView.setScale(0.3f);

            // lottieAnimationView.playAnimation();
//            animationView.loop(true)
            animationView.repeatCount = LottieDrawable.INFINITE
            rl.setOnClickListener {
                animationView.playAnimation()
            }
            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return lottieItemList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return lottieItemList[position].name
        }
    }
}

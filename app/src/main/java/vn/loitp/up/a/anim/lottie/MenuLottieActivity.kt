package vn.loitp.up.a.anim.lottie

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
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FONT_PATH
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.changeTabsFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationLottieBinding
import java.io.IOException

// https://www.lottiefiles.com/?page=1
@LogTag("MenuLottieActivity")
@IsFullScreen(false)
class MenuLottieActivity : BaseActivityFont() {
    private val lottieItemList: MutableList<LottieItem> = ArrayList()
    private lateinit var binding: AAnimationLottieBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationLottieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        prepareData()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuLottieActivity::class.java.simpleName
        }

        val slidePagerAdapter = SlidePagerAdapter()
        binding.viewPager.adapter = slidePagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)

        binding.tabLayout.changeTabsFont(fontName = FONT_PATH)

        binding.sb.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                binding.viewPager.currentItem = seekBar.progress
            }
        })
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}
            override fun onPageSelected(i: Int) {
                binding.sb.progress = i
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
        binding.sb.max = lottieItemList.size
        binding.viewPager.adapter?.notifyDataSetChanged()
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
            val layout = inflater.inflate(R.layout.i_lottie_view, collection, false) as ViewGroup
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

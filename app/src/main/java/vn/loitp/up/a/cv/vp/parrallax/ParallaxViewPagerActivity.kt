package vn.loitp.up.a.cv.vp.parrallax

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
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.getRandomColor
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.vp.parrallax.ParallaxMode
import vn.loitp.R
import vn.loitp.databinding.AVpParallaxBinding

@LogTag("ParallaxViewPagerActivity")
@IsFullScreen(false)
class ParallaxViewPagerActivity : BaseActivityFont() {
    private val resList: MutableList<Int> = ArrayList()
    private lateinit var binding: AVpParallaxBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpParallaxBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = ParallaxViewPagerActivity::class.java.simpleName
        }
        for (i in 0..19) {
            resList.add(getRandomColor())
        }
        binding.viewPager.parrallaxMode = ParallaxMode.RIGHT_OVERLAY
        binding.viewPager.adapter = SlidePagerAdapter()
    }

    private inner class SlidePagerAdapter : PagerAdapter() {
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val res = resList[position]
            logD("res $res")
            val inflater = LayoutInflater.from(this@ParallaxViewPagerActivity)
            val layout =
                inflater.inflate(R.layout.item_photo_slide_iv, collection, false) as ViewGroup
            val imageView = layout.findViewById<ImageView>(R.id.imageView)
            if (position % 2 == 0) {
                imageView.setImageResource(R.drawable.iv)
            } else {
                imageView.setImageResource(R.drawable.logo)
            }
            val textView = layout.findViewById<TextView>(R.id.textView)
            textView.text = position.toString() + "/" + resList.size
            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun getCount(): Int {
            return resList.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}

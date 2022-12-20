package vn.loitp.app.activity.customviews.viewPager.parrallax

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
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LStoreUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.viewPager.parrallax.ParallaxMode
import kotlinx.android.synthetic.main.activity_view_pager_parallax.*
import vn.loitp.app.R

@LogTag("ParallaxViewPagerActivity")
@IsFullScreen(false)
class ParallaxViewPagerActivity : BaseFontActivity() {
    private val resList: MutableList<Int> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_parallax
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
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
            this.tvTitle?.text = ParallaxViewPagerActivity::class.java.simpleName
        }
        for (i in 0..19) {
            resList.add(LStoreUtil.randomColor)
        }
        viewPager.parrallaxMode = ParallaxMode.RIGHT_OVERLAY
        viewPager.adapter = SlidePagerAdapter()
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

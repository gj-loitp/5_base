package vn.loitp.a.cv.vp.detectSwipeOut

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.getRandomColor
import com.loitp.core.ext.setPullLikeIOSHorizontal
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_vp_detect_swipe_out.*
import vn.loitp.R

@LogTag("DetectViewPagerSwipeOutActivity")
@IsFullScreen(false)
class DetectViewPagerSwipeOutActivityFont : BaseActivityFont() {
    private val vpPhotoList: MutableList<VPPhoto> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_vp_detect_swipe_out
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = DetectViewPagerSwipeOutActivityFont::class.java.simpleName
        }
        val max = 3
        for (i in 0 until max) {
            val vpPhoto = VPPhoto()
            vpPhoto.color = getRandomColor()
            vpPhoto.string = "Page " + i + "/" + (max - 1)
            vpPhotoList.add(vpPhoto)
        }
        viewPager.setPullLikeIOSHorizontal(
            onUpOrLeft = { offset ->
                logD("onUpOrLeft $offset")
                showShortInformation("Detect Left")
            },
            onUpOrLeftRefresh = { offset ->
                logD("onUpOrLeftRefresh $offset")
            },
            onDownOrRight = { offset ->
                logD("onDownOrRight $offset")
                showShortInformation("Detect Right")
            },
            onDownOrRightRefresh = { offset ->
                logD("onDownOrRightRefresh $offset")
            }
        )
        val adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private inner class ViewPagerAdapter(fm: FragmentManager) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val bundle = Bundle()
            bundle.putSerializable("vpphoto", vpPhotoList[position])
            val fragment = FrmPhoto()
            fragment.arguments = bundle
            return fragment
        }

        override fun getCount(): Int {
            return 3
        }
    }
}

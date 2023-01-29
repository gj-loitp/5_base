package vn.loitp.a.cv.vp.vertical

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_vp_vertical.*
import vn.loitp.R

@LogTag("ViewPagerVerticalActivity")
@IsFullScreen(false)
class ViewPagerVerticalActivity : BaseActivityFont() {

    private val stringList: MutableList<String> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_vp_vertical
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addData()
        setupViews()
    }

    private fun addData() {
        for (i in 0..7) {
            stringList.add(i.toString())
        }
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerVerticalActivity::class.java.simpleName
        }
        viewPager.adapter = VerticalAdapter(
            supportFragmentManager,
            stringList
        )
    }
}

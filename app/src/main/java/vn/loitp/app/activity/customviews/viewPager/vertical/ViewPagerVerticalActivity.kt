package vn.loitp.app.activity.customviews.viewPager.vertical

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_view_pager_vertical.*
import vn.loitp.app.R

@LogTag("ViewPagerVerticalActivity")
@IsFullScreen(false)
class ViewPagerVerticalActivity : BaseFontActivity() {

    private val stringList: MutableList<String> = ArrayList()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_vertical
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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
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

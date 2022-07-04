package vn.loitp.app.activity.customviews.viewPager.vertical

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
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
        viewPager.adapter = VerticalAdapter(
            supportFragmentManager,
            stringList
        )
    }

    private fun addData() {
        for (i in 0..7) {
            stringList.add(i.toString())
        }
    }
}

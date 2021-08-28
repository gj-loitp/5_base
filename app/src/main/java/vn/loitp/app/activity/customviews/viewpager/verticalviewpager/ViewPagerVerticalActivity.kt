package vn.loitp.app.activity.customviews.viewpager.verticalviewpager

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_view_pager_vertical.*
import vn.loitp.app.R
import java.util.*

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
        viewPager.adapter =
            VerticalAdapter(
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

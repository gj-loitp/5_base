package vn.loitp.app.a.cv.vp.easyFlip

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioGroup
import androidx.viewpager2.widget.ViewPager2
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2
import kotlinx.android.synthetic.main.activity_view_pager_2_demo.*
import vn.loitp.R

@LogTag("ViewPager2DemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ViewPager2DemoActivity : BaseFontActivity() {

    private lateinit var rgOrientation: RadioGroup
    private val itemsList = arrayListOf<Int>()
    private var sliderAdapter = ScreenSlideRecyclerAdapter(itemsList)

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_pager_2_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    fun setupViews() {
        // Items
        val colors = intArrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN)
        sliderAdapter.setItems(colors.toList())

        // ViewPager
        viewPager2.adapter = sliderAdapter

        val bookTransformer = BookFlipPageTransformer2()
        viewPager2.setPageTransformer(bookTransformer)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // RadioGroup
        rgOrientation = findViewById(R.id.rgOrientation)
        rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioHorizontal)
                viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            else
                viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
    }
}

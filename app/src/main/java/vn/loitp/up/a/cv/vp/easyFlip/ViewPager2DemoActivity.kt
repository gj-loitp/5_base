package vn.loitp.up.a.cv.vp.easyFlip

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioGroup
import androidx.viewpager2.widget.ViewPager2
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer2
import vn.loitp.R
import vn.loitp.databinding.AVp2DemoBinding

@LogTag("ViewPager2DemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ViewPager2DemoActivity : BaseActivityFont() {
    private lateinit var binding: AVp2DemoBinding
    private lateinit var rgOrientation: RadioGroup
    private val itemsList = arrayListOf<Int>()
    private var sliderAdapter = ScreenSlideRecyclerAdapter(itemsList)

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVp2DemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    fun setupViews() {
        // Items
        val colors = intArrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.CYAN)
        sliderAdapter.setItems(colors.toList())

        // ViewPager
        binding.viewPager2.adapter = sliderAdapter

        val bookTransformer = BookFlipPageTransformer2()
        binding.viewPager2.setPageTransformer(bookTransformer)
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // RadioGroup
        rgOrientation = findViewById(R.id.rgOrientation)
        rgOrientation.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioHorizontal) binding.viewPager2.orientation =
                ViewPager2.ORIENTATION_HORIZONTAL
            else binding.viewPager2.orientation = ViewPager2.ORIENTATION_VERTICAL
        }
    }
}

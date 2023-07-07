package vn.loitp.up.a.cv.vp.vertical

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AVpVerticalBinding

@LogTag("ViewPagerVerticalActivity")
@IsFullScreen(false)
class ViewPagerVerticalActivity : BaseActivityFont() {
    private lateinit var binding: AVpVerticalBinding
    private val stringList: MutableList<String> = ArrayList()

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AVpVerticalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addData()
        setupViews()
    }

    private fun addData() {
        for (i in 0..7) {
            stringList.add(i.toString())
        }
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ViewPagerVerticalActivity::class.java.simpleName
        }
        binding.viewPager.adapter = VerticalAdapter(
            supportFragmentManager,
            stringList
        )
    }
}

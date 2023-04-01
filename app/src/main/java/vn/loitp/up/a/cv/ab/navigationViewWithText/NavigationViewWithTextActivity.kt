package vn.loitp.up.a.cv.ab.navigationViewWithText

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.views.nav.LTextNavigationView
import vn.loitp.R
import vn.loitp.databinding.ANavigationViewWithTextBinding

@LogTag("NavigationViewWithTextActivity")
@IsFullScreen(false)
class NavigationViewWithTextActivity : BaseActivityFont() {
    private lateinit var binding: ANavigationViewWithTextBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANavigationViewWithTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.nv.apply {
            setTextNext("Next")
            setTextPrev("Prev Prev Prev")
            setTextSize(dpPrev = 22f, dpText = 18f, dpNext = 22f)
            colorOn = getColor(R.color.red)
            colorOff = getColor(R.color.gray)
            tv?.setTextColor(Color.BLACK)
        }

        val stringList = ArrayList<String>()
        for (i in 0..9) {
            stringList.add("Item $i")
        }

        binding.nv.setStringList(stringList)
        binding.nv.setNVCallback(object : LTextNavigationView.NVCallback {
            @SuppressLint("SetTextI18n")
            override fun onIndexChange(index: Int, s: String?) {
                binding.tvMsg.text = "$index -> $s"
            }
        })
        binding.bt0.setOnClickListener { binding.nv.setCurrentIndex(0) }
        binding.bt1.setOnClickListener { binding.nv.setCurrentIndex(stringList.size - 1) }
        binding.bt2.setOnClickListener { binding.nv.setCurrentIndex(2) }
    }
}

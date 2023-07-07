package vn.loitp.up.a.cv.ab.navigationView

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setTextSizePx
import com.loitp.views.nav.LNavigationView
import vn.loitp.R
import vn.loitp.databinding.ANavigationViewBinding

@LogTag("NavigationViewActivity")
@IsFullScreen(false)
class NavigationViewActivity : BaseActivityFont() {
    private lateinit var binding: ANavigationViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.nv.apply {
            colorOn = getColor(R.color.red)
            colorOff = getColor(R.color.gray)
            tv?.setTextColor(Color.BLACK)
            this.tv.setTextSizePx(
                size = resources.getDimension(R.dimen.txt_medium)
            )
        }

        val stringList = ArrayList<String>()
        for (i in 0..9) {
            stringList.add("Item $i")
        }

        binding.nv.setStringList(stringList)
        binding.nv.setNVCallback(
            nvCallback = object : LNavigationView.NVCallback {
                @SuppressLint("SetTextI18n")
                override fun onIndexChange(index: Int, s: String?) {
                    logD("onIndexChange $index -> $s")
                    binding.tvMsg.text = "$index -> $s"
                }
            }
        )
        binding.bt0.setOnClickListener { binding.nv.setCurrentIndex(0) }
        binding.bt1.setOnClickListener { binding.nv.setCurrentIndex(stringList.size - 1) }
        binding.bt2.setOnClickListener { binding.nv.setCurrentIndex(2) }
    }
}

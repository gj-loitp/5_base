package vn.loitp.up.a.cv.wheelSpiner

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.R
import vn.loitp.databinding.AWheelSpinnerBinding

@LogTag("WheelSpinnerActivity")
@IsFullScreen(false)
class WheelSpinnerActivity : BaseActivityFont() {

    private lateinit var binding: AWheelSpinnerBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AWheelSpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        val listImg: MutableList<Int> = ArrayList()
        listImg.add(R.drawable.tatoo_1)
        listImg.add(R.drawable.tatoo_2)
        listImg.add(R.drawable.tatoo_3)
        listImg.add(R.drawable.tatoo_4)
        listImg.add(R.drawable.tatoo_5)
        listImg.add(R.drawable.tatoo_6)

        binding.wheelSpinner.setArrowPointer(binding.ivArrow)
        binding.wheelSpinner.setBitmapsId(listImg)
        binding.wheelSpinner.setOnItemSelectListener { bitmap: Bitmap? ->
            binding.ivSelectedTattoo.setImageBitmap(bitmap)
            binding.ivSelectedTattoo.visibility = View.VISIBLE
        }

        binding.btnSpin.setSafeOnClickListener {
            binding.ivSelectedTattoo.visibility = View.GONE
            binding.wheelSpinner.rotateWheel()
        }
    }
}

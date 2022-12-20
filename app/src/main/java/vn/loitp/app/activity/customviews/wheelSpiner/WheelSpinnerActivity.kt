package vn.loitp.app.activity.customviews.wheelSpiner

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_wheel_spinner.*
import vn.loitp.app.R

@LogTag("WheelSpinnerActivity")
@IsFullScreen(false)
class WheelSpinnerActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_wheel_spinner
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        wheelSpinner.setArrowPointer(ivArrow)
        wheelSpinner.setBitmapsId(listImg)
        wheelSpinner.setOnItemSelectListener { bitmap: Bitmap? ->
            ivSelectedTattoo.setImageBitmap(bitmap)
            ivSelectedTattoo.visibility = View.VISIBLE
        }

        btnSpin.setSafeOnClickListener {
            ivSelectedTattoo.visibility = View.GONE
            wheelSpinner.rotateWheel()
        }
    }
}

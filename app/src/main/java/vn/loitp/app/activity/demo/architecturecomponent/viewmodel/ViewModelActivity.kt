package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_view_model.*
import loitp.basemaster.R
import java.util.*

//https://codinginfinite.com/architecture-component-viewmodel-example/
class ViewModelActivity : BaseFontActivity() {

    private lateinit var colorChangerViewModel: ColorChangerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        colorChangerViewModel = ViewModelProviders.of(this).get(ColorChangerViewModel::class.java)
        ll.setBackgroundColor(colorChangerViewModel.getColorResource())

        btChangeColor.setOnClickListener {
            val color = generateRandomColor()
            ll.setBackgroundColor(color)
            colorChangerViewModel.setColorResource(color)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_view_model
    }

    private fun generateRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}

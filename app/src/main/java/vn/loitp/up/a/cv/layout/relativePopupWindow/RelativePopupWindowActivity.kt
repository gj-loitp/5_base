package vn.loitp.up.a.cv.layout.relativePopupWindow

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALayoutRelativePopupWindowBinding

@LogTag("RelativePopupWindowActivity")
@IsFullScreen(false)
class RelativePopupWindowActivity : BaseActivityFont() {
    private lateinit var binding: ALayoutRelativePopupWindowBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutRelativePopupWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = RelativePopupWindowActivity::class.java.simpleName
        }
        val adapterVertical = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapterVertical.addAll(*resources.getStringArray(R.array.vertical_positions))
        adapterVertical.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerVertical.adapter = adapterVertical

        val adapterHorizontal = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapterHorizontal.addAll(*resources.getStringArray(R.array.horizontal_positions))
        adapterHorizontal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHorizontal.adapter = adapterHorizontal

        val adapterWidth = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapterWidth.addAll(*resources.getStringArray(R.array.width))
        adapterWidth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerWidth.adapter = adapterWidth

        val adapterHeight = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapterHeight.addAll(*resources.getStringArray(R.array.height))
        adapterHeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerHeight.adapter = adapterHeight

        binding.button1.setOnClickListener { view: View ->
            val popup = ExampleCardPopup(view.context)
            when (binding.spinnerWidth.selectedItemPosition) {
                0 -> popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                1 -> popup.width = ViewGroup.LayoutParams.MATCH_PARENT
                2 -> popup.width = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    80f,
                    resources.displayMetrics
                ).toInt()
                3 -> popup.width = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    240f,
                    resources.displayMetrics
                ).toInt()
                4 -> popup.width = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    480f,
                    resources.displayMetrics
                ).toInt()
                else -> throw IllegalStateException()
            }
            when (binding.spinnerWidth.selectedItemPosition) {
                0 -> popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
                1 -> popup.height = ViewGroup.LayoutParams.MATCH_PARENT
                2 -> popup.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    80f,
                    resources.displayMetrics
                ).toInt()
                3 -> popup.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    240f,
                    resources.displayMetrics
                ).toInt()
                4 -> popup.height = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    480f,
                    resources.displayMetrics
                ).toInt()
                else -> throw IllegalStateException()
            }
            val verticalPos: Int = when (binding.spinnerVertical.selectedItemPosition) {
                0 -> RelativePopupWindow.VerticalPosition.ABOVE
                1 -> RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM
                2 -> RelativePopupWindow.VerticalPosition.CENTER
                3 -> RelativePopupWindow.VerticalPosition.ALIGN_TOP
                4 -> RelativePopupWindow.VerticalPosition.BELOW
                else -> throw IllegalStateException()
            }
            val horizontalPos: Int = when (binding.spinnerHorizontal.selectedItemPosition) {
                0 -> RelativePopupWindow.HorizontalPosition.LEFT
                1 -> RelativePopupWindow.HorizontalPosition.ALIGN_RIGHT
                2 -> RelativePopupWindow.HorizontalPosition.CENTER
                3 -> RelativePopupWindow.HorizontalPosition.ALIGN_LEFT
                4 -> RelativePopupWindow.HorizontalPosition.RIGHT
                else -> throw IllegalStateException()
            }
            val fitInScreen = binding.checkboxFitInScreen.isChecked
            popup.showOnAnchor(view, verticalPos, horizontalPos, fitInScreen)
        }
    }
}

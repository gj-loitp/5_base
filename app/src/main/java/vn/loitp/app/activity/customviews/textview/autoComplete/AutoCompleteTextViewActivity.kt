package vn.loitp.app.activity.customviews.textview.autoComplete

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_text_view_auto_complete.*
import vn.loitp.app.R

@LogTag("AutoCompleteTextViewActivity")
@IsFullScreen(false)
class AutoCompleteTextViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_auto_complete
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = AutoCompleteTextViewActivity::class.java.simpleName
        }

        val fruits = arrayOf("Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear")
        // Creating the instance of ArrayAdapter containing list of fruit names
        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, fruits)
        // Getting the instance of AutoCompleteTextView
        // Getting the instance of AutoCompleteTextView
        autoCompleteTextView.threshold = 1 // will start working from first character
        autoCompleteTextView.setAdapter(adapter) // setting the adapter data into the AutoCompleteTextView
        autoCompleteTextView.setTextColor(Color.RED)
    }
}

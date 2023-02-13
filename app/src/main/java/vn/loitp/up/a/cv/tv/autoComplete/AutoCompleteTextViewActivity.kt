package vn.loitp.up.a.cv.tv.autoComplete

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ATvAutoCompleteBinding

@LogTag("AutoCompleteTextViewActivity")
@IsFullScreen(false)
class AutoCompleteTextViewActivity : BaseActivityFont() {
    private lateinit var binding: ATvAutoCompleteBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvAutoCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
        binding.autoCompleteTextView.threshold = 1 // will start working from first character
        binding.autoCompleteTextView.setAdapter(adapter) // setting the adapter data into the AutoCompleteTextView
        binding.autoCompleteTextView.setTextColor(Color.RED)
    }
}

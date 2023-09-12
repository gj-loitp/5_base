package vn.loitp.up.a.cv.layout.expansionPanel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.github.florent37.expansionpanel.ExpansionHeader
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AExpansionPanelSampleProgrammaticallyBinding
import vn.loitp.up.a.cv.layout.expansionPanel.Utils.dpToPx

@LogTag("ExpansionPanelSampleActivityProgrammatically")
@IsFullScreen(false)
class ExpansionPanelSampleActivityProgrammatically : BaseActivityFont() {
    private lateinit var binding: AExpansionPanelSampleProgrammaticallyBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AExpansionPanelSampleProgrammaticallyBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ExpansionPanelSampleActivityProgrammatically::class.java.simpleName
        }

        val ex1 = addDynamicLayout()
        val ex2 = addDynamicLayout()

        // example of how to add a listener
        ex1.addListener { _, _ ->
        }
        val expansionLayoutCollection = ExpansionLayoutCollection()
        expansionLayoutCollection.add(ex1).add(ex2)
        expansionLayoutCollection.openOnlyOne(true)
    }

    private fun addDynamicLayout(): ExpansionLayout {
        val expansionHeader = createExpansionHeader()
        binding.dynamicLayoutContainer.addView(
            expansionHeader,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val expansionLayout = createExpansionLayout()
        binding.dynamicLayoutContainer.addView(
            expansionLayout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        expansionHeader.setExpansionLayout(expansionLayout)
        return expansionLayout
    }

    @SuppressLint("SetTextI18n")
    private fun createExpansionLayout(): ExpansionLayout {
        val expansionLayout = ExpansionLayout(this)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        expansionLayout.addView(
            layout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            dpToPx(this, 48f)
        ) // equivalent to addView(linearLayout)
        val text = TextView(this)
        text.text = "Click to add view"
        text.gravity = Gravity.CENTER
        text.setTextColor(Color.parseColor("#3E3E3E"))
        text.setBackgroundColor(Color.parseColor("#EEEEEE"))
        layout.addView(text, ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(this, 200f))
        text.setOnClickListener {
            val child = TextView(this@ExpansionPanelSampleActivityProgrammatically)
            child.setBackgroundColor(Color.GRAY)
            child.text = System.currentTimeMillis().toString()
            child.setTextColor(Color.WHITE)
            layout.addView(child, ViewGroup.LayoutParams.MATCH_PARENT, 100)
        }
        layout.addView(
            LayoutInflater.from(this)
                .inflate(R.layout.view_expansion_panel_sample_panel, layout, false)
        )
        return expansionLayout
    }

    @SuppressLint("SetTextI18n")
    private fun createExpansionHeader(): ExpansionHeader {
        val expansionHeader = ExpansionHeader(this)
        expansionHeader.setBackgroundColor(Color.WHITE)
        expansionHeader.setPadding(
            dpToPx(this, 16f),
            dpToPx(this, 8f),
            dpToPx(this, 16f),
            dpToPx(this, 8f)
        )
        val layout = RelativeLayout(this)
        expansionHeader.addView(
            layout,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ) // equivalent to addView(linearLayout)
        // image
        val expansionIndicator: ImageView = AppCompatImageView(this)
        expansionIndicator.setImageResource(com.github.florent37.expansionpanel.R.drawable.ic_expansion_header_indicator_grey_24dp)
        val imageLayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        imageLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        imageLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        layout.addView(expansionIndicator, imageLayoutParams)
        // label
        val text = TextView(this)
        text.text = "Trip name"
        text.setTextColor(Color.parseColor("#3E3E3E"))
        val textLayoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        layout.addView(text, textLayoutParams)
        expansionHeader.setExpansionHeaderIndicator(expansionIndicator)
        return expansionHeader
    }
}

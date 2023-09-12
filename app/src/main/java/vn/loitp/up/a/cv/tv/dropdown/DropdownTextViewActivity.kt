package vn.loitp.up.a.cv.tv.dropdown

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import hakobastvatsatryan.DropdownTextView
import vn.loitp.R
import vn.loitp.databinding.ATvDropdownBinding

@LogTag("DropdownTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class DropdownTextViewActivity : BaseActivityFont() {
    private lateinit var binding: ATvDropdownBinding

    private val text = "Click to visit <a href=\"en/page\"> Page </a>"

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvDropdownBinding.inflate(layoutInflater)
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
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/hakobast/DropdownTextView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DropdownTextViewActivity::class.java.simpleName
        }

        binding.secondDropdownTextView.setTitleText(R.string.dropdown_title_text)
        binding.secondDropdownTextView.setContentText(R.string.dropdown_content_text)
        val thirdDropdownTextViewSecond = DropdownTextView.Builder(this)
            .setTitleTextColorRes(R.color.third_drop_down_text_view_title)
            .setTitleTextColorExpandedRes(R.color.third_drop_down_text_view_title_expanded)
            .setTitleTextRes(R.string.dropdown_title_text)
            .setContentTextColorRes(R.color.third_drop_down_text_view_content)
            .setLinkUnderline(true)
            .setLinkTextColorRes(R.color.first_drop_down_text_view_title)
            .setHtmlContent(text) { url ->
                showShortInformation("LINK $url")
            }
            .setRegularBackgroundDrawableRes(R.drawable.bg_third_dropdown_text_view_regular)
            .setExpandedBackgroundDrawableRes(R.drawable.bg_third_dropdown_text_view_expanded)
            .build()

        binding.rootView.addView(
            thirdDropdownTextViewSecond,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                resources.getDimension(com.loitp.R.dimen.margin_padding_medium).toInt().apply {
                    setMargins(this, this, this, this)
                }
            })
    }
}

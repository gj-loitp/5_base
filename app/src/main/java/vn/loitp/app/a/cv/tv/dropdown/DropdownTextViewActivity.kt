package vn.loitp.app.a.cv.tv.dropdown

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import hakobastvatsatryan.DropdownTextView
import kotlinx.android.synthetic.main.activity_dropdown_text_view.*
import vn.loitp.R

@LogTag("DropdownTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class DropdownTextViewActivity : BaseFontActivity() {

    private val text = "Click to visit <a href=\"en/page\"> Page </a>"

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_dropdown_text_view
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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/hakobast/DropdownTextView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DropdownTextViewActivity::class.java.simpleName
        }

        secondDropdownTextView.setTitleText(R.string.dropdown_title_text)
        secondDropdownTextView.setContentText(R.string.dropdown_content_text)
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

        rootView.addView(
            thirdDropdownTextViewSecond,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                resources.getDimension(R.dimen.margin_padding_medium).toInt().apply {
                    setMargins(this, this, this, this)
                }
            })
    }
}

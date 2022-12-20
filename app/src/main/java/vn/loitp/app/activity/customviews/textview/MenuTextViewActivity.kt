package vn.loitp.app.activity.customviews.textview

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_text_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.textview.autoComplete.AutoCompleteTextViewActivity
import vn.loitp.app.activity.customviews.textview.autoFit.AutoFitTextViewActivity
import vn.loitp.app.activity.customviews.textview.colorTextView.ColorTextViewActivity
import vn.loitp.app.activity.customviews.textview.countDown.CountDownActivity
import vn.loitp.app.activity.customviews.textview.dropdownTextview.DropdownTextViewActivity
import vn.loitp.app.activity.customviews.textview.extraTextView.ExtraTextViewActivity
import vn.loitp.app.activity.customviews.textview.fadingTextView.FadingTextViewActivity
import vn.loitp.app.activity.customviews.textview.justified.JustifiedTextViewActivity
import vn.loitp.app.activity.customviews.textview.readMoreOption.ReadMoreOptionActivity
import vn.loitp.app.activity.customviews.textview.scoreText.ScoreTextViewActivity
import vn.loitp.app.activity.customviews.textview.scrollNumber.ScrollNumberActivity
import vn.loitp.app.activity.customviews.textview.selectableTextView.SelectableTextViewActivity
import vn.loitp.app.activity.customviews.textview.strokedTextView.StrokedTextViewActivity
import vn.loitp.app.activity.customviews.textview.textDecorator.TextDecoratorActivity
import vn.loitp.app.activity.customviews.textview.textarc.TextArcActivity
import vn.loitp.app.activity.customviews.textview.typeWriterTextView.TypeWriterTextViewActivity
import vn.loitp.app.activity.customviews.textview.typedTextView.TypedTextViewActivity
import vn.loitp.app.activity.customviews.textview.verticalMarquee.VerticalMarqueeTextViewActivity
import vn.loitp.app.activity.customviews.textview.zoom.ZoomTextViewActivity

@LogTag("TextViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuTextViewActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_text_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuTextViewActivity::class.java.simpleName
        }
        btAutoCompleteTextView.setOnClickListener(this)
        btAutofitTextView.setOnClickListener(this)
        btScoreText.setOnClickListener(this)
        btCountDown.setOnClickListener(this)
        btColorTextView.setOnClickListener(this)
        btScrollNumber.setOnClickListener(this)
        btSelectableTextView.setOnClickListener(this)
        btZoomTextView.setOnClickListener(this)
        btVerticalMarqueeTextView.setOnClickListener(this)
        btTypeWriterTextView.setOnClickListener(this)
        btTextDecorator.setOnClickListener(this)
        btExtraTextview.setOnClickListener(this)
        btStrokedTextView.setOnClickListener(this)
        btJustifiedTextViewActivity.setOnClickListener(this)
        btFadingTextView.setOnClickListener(this)
        btTypedTextView.setOnClickListener(this)
        btDropdownTextView.setOnClickListener(this)
        btTextArcActivity.setOnClickListener(this)
        btReadMoreOptionActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btAutoCompleteTextView -> launchActivity(AutoCompleteTextViewActivity::class.java)
            btAutofitTextView -> launchActivity(AutoFitTextViewActivity::class.java)
            btScoreText -> launchActivity(ScoreTextViewActivity::class.java)
            btCountDown -> launchActivity(CountDownActivity::class.java)
            btColorTextView -> launchActivity(ColorTextViewActivity::class.java)
            btScrollNumber -> launchActivity(ScrollNumberActivity::class.java)
            btSelectableTextView -> launchActivity(SelectableTextViewActivity::class.java)
            btZoomTextView -> launchActivity(ZoomTextViewActivity::class.java)
            btVerticalMarqueeTextView -> launchActivity(VerticalMarqueeTextViewActivity::class.java)
            btTypeWriterTextView -> launchActivity(TypeWriterTextViewActivity::class.java)
            btTextDecorator -> launchActivity(TextDecoratorActivity::class.java)
            btExtraTextview -> launchActivity(ExtraTextViewActivity::class.java)
            btStrokedTextView -> launchActivity(StrokedTextViewActivity::class.java)
            btJustifiedTextViewActivity -> launchActivity(JustifiedTextViewActivity::class.java)
            btFadingTextView -> launchActivity(FadingTextViewActivity::class.java)
            btTypedTextView -> launchActivity(TypedTextViewActivity::class.java)
            btDropdownTextView -> launchActivity(DropdownTextViewActivity::class.java)
            btTextArcActivity -> launchActivity(TextArcActivity::class.java)
            btReadMoreOptionActivity -> launchActivity(ReadMoreOptionActivity::class.java)
        }
    }
}

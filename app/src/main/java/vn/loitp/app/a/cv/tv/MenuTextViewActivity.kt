package vn.loitp.app.a.cv.tv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_text_view.*
import vn.loitp.R
import vn.loitp.app.a.cv.tv.autoComplete.AutoCompleteTextViewActivity
import vn.loitp.app.a.cv.tv.autoFit.AutoFitTextViewActivity
import vn.loitp.app.a.cv.tv.color.ColorTextViewActivity
import vn.loitp.app.a.cv.tv.countDown.CountDownActivity
import vn.loitp.app.a.cv.tv.dropdown.DropdownTextViewActivity
import vn.loitp.app.a.cv.tv.extra.ExtraTextViewActivity
import vn.loitp.app.a.cv.tv.fading.FadingTextViewActivity
import vn.loitp.app.a.cv.tv.justified.JustifiedTextViewActivity
import vn.loitp.app.a.cv.tv.readMoreOption.ReadMoreOptionActivity
import vn.loitp.app.a.cv.tv.score.ScoreTextViewActivity
import vn.loitp.app.a.cv.tv.scrollNumber.ScrollNumberActivity
import vn.loitp.app.a.cv.tv.selectable.SelectableTextViewActivity
import vn.loitp.app.a.cv.tv.stroked.StrokedTextViewActivity
import vn.loitp.app.a.cv.tv.textDecorator.TextDecoratorActivity
import vn.loitp.app.a.cv.tv.textArc.TextArcActivity
import vn.loitp.app.a.cv.tv.typeWrite.TypeWriterTextViewActivity
import vn.loitp.app.a.cv.tv.typed.TypedTextViewActivity
import vn.loitp.app.a.cv.tv.verticalMarquee.VerticalMarqueeTextViewActivity
import vn.loitp.app.a.cv.tv.zoom.ZoomTextViewActivity

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

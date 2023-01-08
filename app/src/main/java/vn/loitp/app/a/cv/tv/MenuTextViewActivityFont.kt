package vn.loitp.app.a.cv.tv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.activity_menu_text_view.*
import vn.loitp.R
import vn.loitp.app.a.cv.tv.autoComplete.AutoCompleteTextViewActivityFont
import vn.loitp.app.a.cv.tv.autoFit.AutoFitTextViewActivityFont
import vn.loitp.app.a.cv.tv.color.ColorTextViewActivityFont
import vn.loitp.app.a.cv.tv.countDown.CountDownActivityFont
import vn.loitp.app.a.cv.tv.dropdown.DropdownTextViewActivityFont
import vn.loitp.app.a.cv.tv.extra.ExtraTextViewActivityFont
import vn.loitp.app.a.cv.tv.fading.FadingTextViewActivityFont
import vn.loitp.app.a.cv.tv.justified.JustifiedTextViewActivityFont
import vn.loitp.app.a.cv.tv.readMoreOption.ReadMoreOptionActivityFont
import vn.loitp.app.a.cv.tv.score.ScoreTextViewActivityFont
import vn.loitp.app.a.cv.tv.scrollNumber.ScrollNumberActivityFont
import vn.loitp.app.a.cv.tv.selectable.SelectableTextViewActivityFont
import vn.loitp.app.a.cv.tv.stroked.StrokedTextViewActivityFont
import vn.loitp.app.a.cv.tv.textArc.TextArcActivityFont
import vn.loitp.app.a.cv.tv.textDecorator.TextDecoratorActivityFont
import vn.loitp.app.a.cv.tv.typeWrite.TypeWriterTextViewActivityFont
import vn.loitp.app.a.cv.tv.typed.TypedTextViewActivityFont
import vn.loitp.app.a.cv.tv.verticalMarquee.VerticalMarqueeTextViewActivityFont
import vn.loitp.app.a.cv.tv.zoom.ZoomTextViewActivityFont

@LogTag("TextViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuTextViewActivityFont : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_text_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuTextViewActivityFont::class.java.simpleName
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
            btAutoCompleteTextView -> launchActivity(AutoCompleteTextViewActivityFont::class.java)
            btAutofitTextView -> launchActivity(AutoFitTextViewActivityFont::class.java)
            btScoreText -> launchActivity(ScoreTextViewActivityFont::class.java)
            btCountDown -> launchActivity(CountDownActivityFont::class.java)
            btColorTextView -> launchActivity(ColorTextViewActivityFont::class.java)
            btScrollNumber -> launchActivity(ScrollNumberActivityFont::class.java)
            btSelectableTextView -> launchActivity(SelectableTextViewActivityFont::class.java)
            btZoomTextView -> launchActivity(ZoomTextViewActivityFont::class.java)
            btVerticalMarqueeTextView -> launchActivity(VerticalMarqueeTextViewActivityFont::class.java)
            btTypeWriterTextView -> launchActivity(TypeWriterTextViewActivityFont::class.java)
            btTextDecorator -> launchActivity(TextDecoratorActivityFont::class.java)
            btExtraTextview -> launchActivity(ExtraTextViewActivityFont::class.java)
            btStrokedTextView -> launchActivity(StrokedTextViewActivityFont::class.java)
            btJustifiedTextViewActivity -> launchActivity(JustifiedTextViewActivityFont::class.java)
            btFadingTextView -> launchActivity(FadingTextViewActivityFont::class.java)
            btTypedTextView -> launchActivity(TypedTextViewActivityFont::class.java)
            btDropdownTextView -> launchActivity(DropdownTextViewActivityFont::class.java)
            btTextArcActivity -> launchActivity(TextArcActivityFont::class.java)
            btReadMoreOptionActivity -> launchActivity(ReadMoreOptionActivityFont::class.java)
        }
    }
}

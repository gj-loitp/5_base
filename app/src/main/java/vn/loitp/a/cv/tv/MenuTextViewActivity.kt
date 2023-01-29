package vn.loitp.a.cv.tv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_menu_tv.*
import vn.loitp.R
import vn.loitp.a.cv.tv.autoComplete.AutoCompleteTextViewActivity
import vn.loitp.a.cv.tv.autoFit.AutoFitTextViewActivity
import vn.loitp.a.cv.tv.color.ColorTextViewActivity
import vn.loitp.a.cv.tv.countDown.CountDownActivity
import vn.loitp.a.cv.tv.dropdown.DropdownTextViewActivity
import vn.loitp.a.cv.tv.extra.ExtraTextViewActivity
import vn.loitp.a.cv.tv.fading.FadingTextViewActivity
import vn.loitp.a.cv.tv.justified.JustifiedTextViewActivityFont
import vn.loitp.a.cv.tv.readMoreOption.ReadMoreOptionActivityFont
import vn.loitp.a.cv.tv.score.ScoreTextViewActivityFont
import vn.loitp.a.cv.tv.scrollNumber.ScrollNumberActivityFont
import vn.loitp.a.cv.tv.selectable.SelectableTextViewActivityFont
import vn.loitp.a.cv.tv.stroked.StrokedTextViewActivityFont
import vn.loitp.a.cv.tv.textArc.TextArcActivityFont
import vn.loitp.a.cv.tv.textDecorator.TextDecoratorActivityFont
import vn.loitp.a.cv.tv.typeWrite.TypeWriterTextViewActivityFont
import vn.loitp.a.cv.tv.typed.TypedTextViewActivityFont
import vn.loitp.a.cv.tv.verticalMarquee.VerticalMarqueeTextViewActivityFont
import vn.loitp.a.cv.tv.zoom.ZoomTextViewActivityFont

@LogTag("TextViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuTextViewActivity : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_tv
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
            btScoreText -> launchActivity(ScoreTextViewActivityFont::class.java)
            btCountDown -> launchActivity(CountDownActivity::class.java)
            btColorTextView -> launchActivity(ColorTextViewActivity::class.java)
            btScrollNumber -> launchActivity(ScrollNumberActivityFont::class.java)
            btSelectableTextView -> launchActivity(SelectableTextViewActivityFont::class.java)
            btZoomTextView -> launchActivity(ZoomTextViewActivityFont::class.java)
            btVerticalMarqueeTextView -> launchActivity(VerticalMarqueeTextViewActivityFont::class.java)
            btTypeWriterTextView -> launchActivity(TypeWriterTextViewActivityFont::class.java)
            btTextDecorator -> launchActivity(TextDecoratorActivityFont::class.java)
            btExtraTextview -> launchActivity(ExtraTextViewActivity::class.java)
            btStrokedTextView -> launchActivity(StrokedTextViewActivityFont::class.java)
            btJustifiedTextViewActivity -> launchActivity(JustifiedTextViewActivityFont::class.java)
            btFadingTextView -> launchActivity(FadingTextViewActivity::class.java)
            btTypedTextView -> launchActivity(TypedTextViewActivityFont::class.java)
            btDropdownTextView -> launchActivity(DropdownTextViewActivity::class.java)
            btTextArcActivity -> launchActivity(TextArcActivityFont::class.java)
            btReadMoreOptionActivity -> launchActivity(ReadMoreOptionActivityFont::class.java)
        }
    }
}

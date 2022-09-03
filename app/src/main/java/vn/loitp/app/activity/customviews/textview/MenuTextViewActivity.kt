package vn.loitp.app.activity.customviews.textview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_text_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.textview.autoComplete.AutoCompleteTextViewActivity
import vn.loitp.app.activity.customviews.textview.autoFit.AutoFitTextViewActivity
import vn.loitp.app.activity.customviews.textview.colorTextView.ColorTextViewActivity
import vn.loitp.app.activity.customviews.textview.countDown.CountDownActivity
import vn.loitp.app.activity.customviews.textview.extraTextView.ExtraTextViewActivity
import vn.loitp.app.activity.customviews.textview.fadingTextView.FadingTextViewActivity
import vn.loitp.app.activity.customviews.textview.justified.JustifiedTextViewActivity
import vn.loitp.app.activity.customviews.textview.scoreText.ScoreTextViewActivity
import vn.loitp.app.activity.customviews.textview.scrollNumber.ScrollNumberActivity
import vn.loitp.app.activity.customviews.textview.selectableTextView.SelectableTextViewActivity
import vn.loitp.app.activity.customviews.textview.strokedTextView.StrokedTextViewActivity
import vn.loitp.app.activity.customviews.textview.textDecorator.TextDecoratorActivity
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
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
    }

    override fun onClick(v: View) {
        val intent: Intent? = when (v) {
            btAutoCompleteTextView -> Intent(this, AutoCompleteTextViewActivity::class.java)
            btAutofitTextView -> Intent(this, AutoFitTextViewActivity::class.java)
            btScoreText -> Intent(this, ScoreTextViewActivity::class.java)
            btCountDown -> Intent(this, CountDownActivity::class.java)
            btColorTextView -> Intent(this, ColorTextViewActivity::class.java)
            btScrollNumber -> Intent(this, ScrollNumberActivity::class.java)
            btSelectableTextView -> Intent(this, SelectableTextViewActivity::class.java)
            btZoomTextView -> Intent(this, ZoomTextViewActivity::class.java)
            btVerticalMarqueeTextView -> Intent(this, VerticalMarqueeTextViewActivity::class.java)
            btTypeWriterTextView -> Intent(this, TypeWriterTextViewActivity::class.java)
            btTextDecorator -> Intent(this, TextDecoratorActivity::class.java)
            btExtraTextview -> Intent(this, ExtraTextViewActivity::class.java)
            btStrokedTextView -> Intent(this, StrokedTextViewActivity::class.java)
            btJustifiedTextViewActivity -> Intent(this, JustifiedTextViewActivity::class.java)
            btFadingTextView -> Intent(this, FadingTextViewActivity::class.java)
            btTypedTextView -> Intent(this, TypedTextViewActivity::class.java)
            else -> {
                null
            }
        }
        intent.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}

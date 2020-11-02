package vn.loitp.app.activity.customviews.textview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_text_view_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.textview.autofittextview.AutoFitTextViewActivity
import vn.loitp.app.activity.customviews.textview.circletextview.CircleTextViewActivity
import vn.loitp.app.activity.customviews.textview.colortextview.ColorTextViewActivity
import vn.loitp.app.activity.customviews.textview.countdown.CountDownActivity
import vn.loitp.app.activity.customviews.textview.extratextview.ExtraTextViewActivity
import vn.loitp.app.activity.customviews.textview.justifiedtextview.JustifiedTextViewActivity
import vn.loitp.app.activity.customviews.textview.scoretext.ScoreTextViewActivity
import vn.loitp.app.activity.customviews.textview.scrollnumber.ScrollNumberActivity
import vn.loitp.app.activity.customviews.textview.selectabletextView.SelectableTextViewActivity
import vn.loitp.app.activity.customviews.textview.strokedtextview.StrokedTextViewActivity
import vn.loitp.app.activity.customviews.textview.textdecorator.TextDecoratorActivity
import vn.loitp.app.activity.customviews.textview.translucentview.TranslucentViewActivity
import vn.loitp.app.activity.customviews.textview.typewritertextview.TypeWriterTextViewActivity
import vn.loitp.app.activity.customviews.textview.verticalmarqueetextview.VerticalMarqueeTextViewActivity
import vn.loitp.app.activity.customviews.textview.zoomtextview.ZoomTextViewActivity

@LogTag("TextViewMenuActivity")
@IsFullScreen(false)
class TextViewMenuActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btAutofitTextView.setOnClickListener(this)
        btCircleTextView.setOnClickListener(this)
        btScoreText.setOnClickListener(this)
        btCountDown.setOnClickListener(this)
        btColorTextView.setOnClickListener(this)
        btScrollNumber.setOnClickListener(this)
        btSelectableTextView.setOnClickListener(this)
        btZoomTextView.setOnClickListener(this)
        btVerticalMarqueeTextView.setOnClickListener(this)
        btTranslucentView.setOnClickListener(this)
        btTypeWriterTextView.setOnClickListener(this)
        btTextDecorator.setOnClickListener(this)
        btJustifieldTextView.setOnClickListener(this)
        btExtraTextview.setOnClickListener(this)
        btStrokedTextView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutofitTextView -> intent = Intent(this, AutoFitTextViewActivity::class.java)
            btCircleTextView -> intent = Intent(this, CircleTextViewActivity::class.java)
            btScoreText -> intent = Intent(this, ScoreTextViewActivity::class.java)
            btCountDown -> intent = Intent(this, CountDownActivity::class.java)
            btColorTextView -> intent = Intent(this, ColorTextViewActivity::class.java)
            btScrollNumber -> intent = Intent(this, ScrollNumberActivity::class.java)
            btSelectableTextView -> intent = Intent(this, SelectableTextViewActivity::class.java)
            btZoomTextView -> intent = Intent(this, ZoomTextViewActivity::class.java)
            btVerticalMarqueeTextView -> intent = Intent(this, VerticalMarqueeTextViewActivity::class.java)
            btTranslucentView -> intent = Intent(this, TranslucentViewActivity::class.java)
            btTypeWriterTextView -> intent = Intent(this, TypeWriterTextViewActivity::class.java)
            btTextDecorator -> intent = Intent(this, TextDecoratorActivity::class.java)
            btJustifieldTextView -> intent = Intent(this, JustifiedTextViewActivity::class.java)
            btExtraTextview -> intent = Intent(this, ExtraTextViewActivity::class.java)
            btStrokedTextView -> intent = Intent(this, StrokedTextViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}

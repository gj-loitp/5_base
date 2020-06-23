package vn.loitp.app.activity.customviews.textview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

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

class TextViewMenuActivity : BaseFontActivity(), OnClickListener {

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

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btAutofitTextView -> intent = Intent(activity, AutoFitTextViewActivity::class.java)
            btCircleTextView -> intent = Intent(activity, CircleTextViewActivity::class.java)
            btScoreText -> intent = Intent(activity, ScoreTextViewActivity::class.java)
            btCountDown -> intent = Intent(activity, CountDownActivity::class.java)
            btColorTextView -> intent = Intent(activity, ColorTextViewActivity::class.java)
            btScrollNumber -> intent = Intent(activity, ScrollNumberActivity::class.java)
            btSelectableTextView -> intent = Intent(activity, SelectableTextViewActivity::class.java)
            btZoomTextView -> intent = Intent(activity, ZoomTextViewActivity::class.java)
            btVerticalMarqueeTextView -> intent = Intent(activity, VerticalMarqueeTextViewActivity::class.java)
            btTranslucentView -> intent = Intent(activity, TranslucentViewActivity::class.java)
            btTypeWriterTextView -> intent = Intent(activity, TypeWriterTextViewActivity::class.java)
            btTextDecorator -> intent = Intent(activity, TextDecoratorActivity::class.java)
            btJustifieldTextView -> intent = Intent(activity, JustifiedTextViewActivity::class.java)
            btExtraTextview -> intent = Intent(activity, ExtraTextViewActivity::class.java)
            btStrokedTextView -> intent = Intent(activity, StrokedTextViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}

package vn.loitp.app.activity.customviews.textview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_textview.*

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
        findViewById<View>(R.id.bt_autofit_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_circle_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_score_text).setOnClickListener(this)
        findViewById<View>(R.id.bt_countdown).setOnClickListener(this)
        findViewById<View>(R.id.bt_color_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_scroll_number).setOnClickListener(this)
        findViewById<View>(R.id.bt_selectable_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_zoom_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_vertical_marquee_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_translucent_view).setOnClickListener(this)
        findViewById<View>(R.id.bt_type_writer_textview).setOnClickListener(this)
        findViewById<View>(R.id.bt_text_decorator).setOnClickListener(this)
        findViewById<View>(R.id.bt_justifield_textview).setOnClickListener(this)

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
        return R.layout.activity_menu_textview
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_autofit_textview -> intent = Intent(activity, AutoFitTextViewActivity::class.java)
            R.id.bt_circle_textview -> intent = Intent(activity, CircleTextViewActivity::class.java)
            R.id.bt_score_text -> intent = Intent(activity, ScoreTextViewActivity::class.java)
            R.id.bt_countdown -> intent = Intent(activity, CountDownActivity::class.java)
            R.id.bt_color_textview -> intent = Intent(activity, ColorTextViewActivity::class.java)
            R.id.bt_scroll_number -> intent = Intent(activity, ScrollNumberActivity::class.java)
            R.id.bt_selectable_textview -> intent = Intent(activity, SelectableTextViewActivity::class.java)
            R.id.bt_zoom_textview -> intent = Intent(activity, ZoomTextViewActivity::class.java)
            R.id.bt_vertical_marquee_textview -> intent = Intent(activity, VerticalMarqueeTextViewActivity::class.java)
            R.id.bt_translucent_view -> intent = Intent(activity, TranslucentViewActivity::class.java)
            R.id.bt_type_writer_textview -> intent = Intent(activity, TypeWriterTextViewActivity::class.java)
            R.id.bt_text_decorator -> intent = Intent(activity, TextDecoratorActivity::class.java)
            R.id.bt_justifield_textview -> intent = Intent(activity, JustifiedTextViewActivity::class.java)
            R.id.btExtraTextview -> intent = Intent(activity, ExtraTextViewActivity::class.java)
            R.id.btStrokedTextView -> intent = Intent(activity, StrokedTextViewActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}

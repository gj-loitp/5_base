package vn.loitp.up.a.cv.tv

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
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
import vn.loitp.databinding.AMenuTvBinding
import vn.loitp.up.a.cv.tv.stroked.StrokedTextViewActivity
import vn.loitp.up.a.cv.tv.textArc.TextArcActivity
import vn.loitp.up.a.cv.tv.textDecorator.TextDecoratorActivity
import vn.loitp.up.a.cv.tv.typeWrite.TypeWriterTextViewActivity
import vn.loitp.up.a.cv.tv.typed.TypedTextViewActivity
import vn.loitp.up.a.cv.tv.verticalMarquee.VerticalMarqueeTextViewActivity
import vn.loitp.up.a.cv.tv.zoom.ZoomTextViewActivity

@LogTag("TextViewMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuTextViewActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: AMenuTvBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuTextViewActivity::class.java.simpleName
        }
        binding.btAutoCompleteTextView.setOnClickListener(this)
        binding.btAutofitTextView.setOnClickListener(this)
        binding.btScoreText.setOnClickListener(this)
        binding.btCountDown.setOnClickListener(this)
        binding.btColorTextView.setOnClickListener(this)
        binding.btScrollNumber.setOnClickListener(this)
        binding.btSelectableTextView.setOnClickListener(this)
        binding.btZoomTextView.setOnClickListener(this)
        binding.btVerticalMarqueeTextView.setOnClickListener(this)
        binding.btTypeWriterTextView.setOnClickListener(this)
        binding.btTextDecorator.setOnClickListener(this)
        binding.btExtraTextview.setOnClickListener(this)
        binding.btStrokedTextView.setOnClickListener(this)
        binding.btJustifiedTextViewActivity.setOnClickListener(this)
        binding.btFadingTextView.setOnClickListener(this)
        binding.btTypedTextView.setOnClickListener(this)
        binding.btDropdownTextView.setOnClickListener(this)
        binding.btTextArcActivity.setOnClickListener(this)
        binding.btReadMoreOptionActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btAutoCompleteTextView -> launchActivity(AutoCompleteTextViewActivity::class.java)
            binding.btAutofitTextView -> launchActivity(AutoFitTextViewActivity::class.java)
            binding.btScoreText -> launchActivity(ScoreTextViewActivityFont::class.java)
            binding.btCountDown -> launchActivity(CountDownActivity::class.java)
            binding.btColorTextView -> launchActivity(ColorTextViewActivity::class.java)
            binding.btScrollNumber -> launchActivity(ScrollNumberActivityFont::class.java)
            binding.btSelectableTextView -> launchActivity(SelectableTextViewActivityFont::class.java)
            binding.btZoomTextView -> launchActivity(ZoomTextViewActivity::class.java)
            binding.btVerticalMarqueeTextView -> launchActivity(VerticalMarqueeTextViewActivity::class.java)
            binding.btTypeWriterTextView -> launchActivity(TypeWriterTextViewActivity::class.java)
            binding.btTextDecorator -> launchActivity(TextDecoratorActivity::class.java)
            binding.btExtraTextview -> launchActivity(ExtraTextViewActivity::class.java)
            binding.btStrokedTextView -> launchActivity(StrokedTextViewActivity::class.java)
            binding.btJustifiedTextViewActivity -> launchActivity(JustifiedTextViewActivityFont::class.java)
            binding.btFadingTextView -> launchActivity(FadingTextViewActivity::class.java)
            binding.btTypedTextView -> launchActivity(TypedTextViewActivity::class.java)
            binding.btDropdownTextView -> launchActivity(DropdownTextViewActivity::class.java)
            binding.btTextArcActivity -> launchActivity(TextArcActivity::class.java)
            binding.btReadMoreOptionActivity -> launchActivity(ReadMoreOptionActivityFont::class.java)
        }
    }
}

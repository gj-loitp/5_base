package vn.loitp.app.a.cv.tv.typed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import com.prush.typedtextview.TypedTextView
import kotlinx.android.synthetic.main.activity_typed_text_view.*
import vn.loitp.R

@LogTag("TypedTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TypedTextViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_typed_text_view
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
                            url = "https://github.com/iamporus/TypedTextView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TypedTextViewActivityFont::class.java.simpleName
        }

        var typedTextView: TypedTextView = findViewById(R.id.typedTextView)
        //Set typing speed
        val builder = TypedTextView.Builder(typedTextView)
            .setTypingSpeed(175)
            .splitSentences(true)
            .setSentencePause(1500)
            .setCursorBlinkSpeed(530)
            .randomizeTypingSpeed(true)
            .showCursor(true)
            .playKeyStrokesAudio(true)
            .randomizeTypeSeed(250)

        typedTextView = builder.build()
        typedTextView.setTypedText("Once there lived a monkey in a jamun tree by a river. The monkey was alone. He had no friends, no family, but he was happy and content.")
        typedTextView.setOnCharacterTypedListener { character, index ->
            logD("onCharacterTyped: $character at index $index")
        }

        //Attach TypedTextView's lifecycle to Activity's lifecycle.
        lifecycle.addObserver(typedTextView.lifecycleObserver)
    }
}
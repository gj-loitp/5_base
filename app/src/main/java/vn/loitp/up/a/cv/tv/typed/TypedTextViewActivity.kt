package vn.loitp.up.a.cv.tv.typed

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.prush.typedtextview.TypedTextView
import vn.loitp.R
import vn.loitp.databinding.ATvTypedBinding

@LogTag("TypedTextViewActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TypedTextViewActivity : BaseActivityFont() {
    private lateinit var binding: ATvTypedBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvTypedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/iamporus/TypedTextView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TypedTextViewActivity::class.java.simpleName
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

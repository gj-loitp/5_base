package vn.loitp.up.a.cv.tv.score

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATvScoreBinding

@LogTag("ScoreTextViewActivity")
@IsFullScreen(false)
class ScoreTextViewActivity : BaseActivityFont() {

    companion object {
        const val KEY_UPDATE_SCORE = 1
        const val DELTA_T = 20 // mls
    }

    private lateinit var binding: ATvScoreBinding
    private val maxScore = 200

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvScoreBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScoreTextViewActivity::class.java.simpleName
        }
        binding.textView.text = "$currentScore"
        binding.bt.setSafeOnClickListener {
            currentScore = 0
            binding.bt.isEnabled = false
            updateScore(maxScore)
        }
    }

    private var currentScore = 0
    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                KEY_UPDATE_SCORE -> {
                    binding.textView.text = msg.obj as String
                    logD(msg.obj.toString())
                    if (msg.obj == maxScore.toString()) {
                        logD("done")
                        binding.bt.isEnabled = true
                    }
                }
            }
        }
    }

    @Suppress("unused")
    private fun updateScore(totalScore: Int) {
        for (i in currentScore..totalScore) {
            handler.sendMessageDelayed(
                handler.obtainMessage(KEY_UPDATE_SCORE, "" + i),
                (i - currentScore) * DELTA_T.toLong()
            )
        }
    }
}

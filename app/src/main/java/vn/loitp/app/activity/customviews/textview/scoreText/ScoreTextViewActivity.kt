package vn.loitp.app.activity.customviews.textview.scoreText

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_text_view_score.*
import vn.loitp.R

@LogTag("ScoreTextViewActivity")
@IsFullScreen(false)
class ScoreTextViewActivity : BaseFontActivity() {

    companion object {
        const val KEY_UPDATE_SCORE = 1
        const val DELTA_T = 20 // mls
    }

    private val maxScore = 200

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_view_score
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = ScoreTextViewActivity::class.java.simpleName
        }
        textView.text = "$currentScore"
        bt.setSafeOnClickListener {
            currentScore = 0
            bt.isEnabled = false
            updateScore(maxScore)
        }
    }

    private var currentScore = 0
    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                KEY_UPDATE_SCORE -> {
                    textView.text = msg.obj as String
                    logD(msg.obj.toString())
                    if (msg.obj == maxScore.toString()) {
                        logD("done")
                        bt.isEnabled = true
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

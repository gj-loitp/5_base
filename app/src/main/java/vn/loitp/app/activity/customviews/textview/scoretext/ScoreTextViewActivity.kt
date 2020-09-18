package vn.loitp.app.activity.customviews.textview.scoretext

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_text_view_score.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_text_view_score)
@LogTag("ScoreTextViewActivity")
class ScoreTextViewActivity : BaseFontActivity() {
    private val maxScore = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView.text = "${currentScore}"
        bt.setOnClickListener {
            currentScore = 0
            bt.isEnabled = false
            updateScore(maxScore)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    private var currentScore = 0
    private var handler: Handler = object : Handler() {
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

    private fun updateScore(totalScore: Int) {
        for (i in currentScore..totalScore) {
            handler.sendMessageDelayed(handler.obtainMessage(KEY_UPDATE_SCORE, "" + i), (i - currentScore) * DELTA_T.toLong())
        }
    }

    companion object {
        const val KEY_UPDATE_SCORE = 1
        const val DELTA_T = 20 //msecond
    }
}

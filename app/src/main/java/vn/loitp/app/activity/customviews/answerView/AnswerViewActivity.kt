package vn.loitp.app.activity.customviews.answerView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.answerView.LAnswerView
import kotlinx.android.synthetic.main.activity_answer_view.*
import vn.loitp.app.R

@LogTag("AnswerViewActivity")
@IsFullScreen(false)
class AnswerViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_answer_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        // use xml
        useXML()
        // use JAVA codes
        useJava()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = AnswerViewActivity::class.java.simpleName
        }
    }

    private fun useXML() {
        lAnswerView1.setNumber(1)
        lAnswerView1.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
            override fun onAnswerChange(view: LAnswerView?, index: Int) {
                showShortInformation("Click: $index")
            }
        })
        lAnswerView1.activeChar = 'A'
        // answerView1.resize(2)

        lAnswerView2.setNumber(2)
        lAnswerView2.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
            override fun onAnswerChange(view: LAnswerView?, index: Int) {
                showShortInformation("Click: $index")
            }
        })
    }

    private fun useJava() {
        for (i in 0..9) {
            val lAnswerView = LAnswerView(this)
            lAnswerView.init(
                number = i + 3,
                numberOfAnswer = 6,
                isShowNumber = true,
                isCanCancelAnswer = true,
                isChangeOnClick = true,
                isShowTextWhenActive = true
            )
            lAnswerView.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
                override fun onAnswerChange(view: LAnswerView?, index: Int) {
                    showShortInformation("Click: $index")
                }
            })
            ll.addView(lAnswerView)
        }
    }
}

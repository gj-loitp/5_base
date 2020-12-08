package vn.loitp.app.activity.customviews.answerview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.LToast
import com.views.answerview.LAnswerView
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

        //use xml
        useXML()

        //use JAVA codes
        useJava()
    }

    private fun useXML() {
        lAnswerView1.setNumber(1)
        lAnswerView1.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
            override fun onAnswerChange(view: LAnswerView?, index: Int) {
                showShortInformation("Click: $index")
            }
        })
        lAnswerView1.activeChar = 'A'
        //answerView1.resize(2)

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

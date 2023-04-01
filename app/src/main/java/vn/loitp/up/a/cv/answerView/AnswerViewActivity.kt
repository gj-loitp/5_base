package vn.loitp.up.a.cv.answerView

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.answerView.LAnswerView
import vn.loitp.R
import vn.loitp.databinding.AAnswerViewBinding

@LogTag("AnswerViewActivity")
@IsFullScreen(false)
class AnswerViewActivity : BaseActivityFont() {
    private lateinit var binding: AAnswerViewBinding

    override fun setLayoutResourceId(): Int {
        return R.layout.a_answer_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnswerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        // use xml
        useXML()
        // use JAVA codes
        useJava()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = AnswerViewActivity::class.java.simpleName
        }
    }

    private fun useXML() {
        binding.lAnswerView1.setNumber(1)
        binding.lAnswerView1.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
            override fun onAnswerChange(view: LAnswerView?, index: Int) {
                showShortInformation("Click: $index")
            }
        })
        binding.lAnswerView1.activeChar = 'A'
        // answerView1.resize(2)

        binding.lAnswerView2.setNumber(2)
        binding.lAnswerView2.setOnAnswerChange(object : LAnswerView.OnAnswerChange {
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
            binding.ll.addView(lAnswerView)
        }
    }
}

package vn.loitp.up.a.func.math

import ClCompiler
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import source.TextSource
import vn.loitp.databinding.AMathBinding

@LogTag("MathActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MathActivity : BaseActivityFont() {

    private lateinit var binding: AMathBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMathBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        doMath()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/ixiDev/MEE"
                    )
                })
                isVisible = true
                setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MathActivity::class.java.simpleName
        }
    }

    @SuppressLint("SetTextI18n")
    private fun doMath() {
        val des = "MEE Math Expression Evaluator"
        val exp = "2^(2+6)*5+log(9+7-ln(6+1))"
        val source = TextSource(exp)
        val compiler = ClCompiler()
        val result = compiler.compile(source)
        binding.tv.text = "$des\n\n\n$exp\n=>\n$result"
    }

}

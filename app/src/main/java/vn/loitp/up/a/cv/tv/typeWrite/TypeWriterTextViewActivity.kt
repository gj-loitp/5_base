package vn.loitp.up.a.cv.tv.typeWrite

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATvTypeWriterBinding

@LogTag("TypeWriterTextViewActivity")
@IsFullScreen(false)
class TypeWriterTextViewActivity : BaseActivityFont() {

    private lateinit var binding: ATvTypeWriterBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATvTypeWriterBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = TypeWriterTextViewActivity::class.java.simpleName
        }
        binding.btn.setSafeOnClickListener {
            binding.textView.text = ""
            binding.textView.setCharacterDelay(150)
            binding.textView.animateText("Type Writer Effect")
        }
    }
}

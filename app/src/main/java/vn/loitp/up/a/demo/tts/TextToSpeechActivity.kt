package vn.loitp.up.a.demo.tts

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.tts.LTextToSpeechUtil
import vn.loitp.R
import vn.loitp.databinding.ADemoTextToSpeechBinding

@LogTag("TextToSpeechActivity")
@IsFullScreen(false)
class TextToSpeechActivity : BaseActivityFont(), OnClickListener {

    private lateinit var binding: ADemoTextToSpeechBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADemoTextToSpeechBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LTextToSpeechUtil.instance.setupTTS()
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
            this.tvTitle?.text = TextToSpeechActivity::class.java.simpleName
        }
        binding.btILoveYou.setOnClickListener(this)
        binding.btYouLoveMe.setOnClickListener(this)
        binding.btSpeak.setOnClickListener(this)

        binding.etType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding.etType.text.toString().isEmpty()) {
                    binding.btSpeak.visibility = View.GONE
                } else {
                    binding.btSpeak.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {
                // do nothing
            }
        })
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btILoveYou -> LTextToSpeechUtil.instance.speakOut("I love you")
            binding.btYouLoveMe -> LTextToSpeechUtil.instance.speakOut("You love me")
            binding.btSpeak -> LTextToSpeechUtil.instance.speakOut(binding.etType.text.toString())
        }
    }

    override fun onDestroy() {
        LTextToSpeechUtil.instance.destroy()
        super.onDestroy()
    }
}

package vn.loitp.app.activity.demo.texttospeech

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
import com.core.base.BaseFontActivity
import com.core.utilities.LTextToSpeechUtil
import kotlinx.android.synthetic.main.activity_text_to_speech.*
import loitp.basemaster.R

class TextToSpeechActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LTextToSpeechUtil.instance.setupTTS(this)
        findViewById<View>(R.id.bt_i_love_you).setOnClickListener(this)
        findViewById<View>(R.id.bt_i_you_love_me).setOnClickListener(this)

        bt_speak.setOnClickListener(this)

        et_type.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (et_type.text.toString().isEmpty()) {
                    bt_speak.visibility = View.GONE
                } else {
                    bt_speak.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable) {
                //do nothing
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_text_to_speech
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_i_love_you -> LTextToSpeechUtil.instance.speakOut("I love you")
            R.id.bt_i_you_love_me -> LTextToSpeechUtil.instance.speakOut("You love me")
            R.id.bt_speak -> LTextToSpeechUtil.instance.speakOut(et_type.text.toString())
        }
    }

    override fun onDestroy() {
        LTextToSpeechUtil.instance.destroy()
        super.onDestroy()
    }
}

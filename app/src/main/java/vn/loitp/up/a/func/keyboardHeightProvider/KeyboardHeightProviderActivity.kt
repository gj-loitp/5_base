package vn.loitp.up.a.func.keyboardHeightProvider

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AKbHeightProviderBinding

@LogTag("KeyboardHeightProviderActivity")
@IsFullScreen(false)
class KeyboardHeightProviderActivity : BaseActivityFont() {

    private lateinit var binding: AKbHeightProviderBinding
    private var keyboardHeightProvider: KeyboardHeightProvider? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AKbHeightProviderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = KeyboardHeightProviderActivity::class.java.simpleName
        }
        keyboardHeightProvider = KeyboardHeightProvider(this)
        keyboardHeightProvider?.addKeyboardListener(object :
            KeyboardHeightProvider.KeyboardListener {
            override fun onHeightChanged(height: Int) {
                if (height == 0) {
                    binding.layoutKeyboardFake.isVisible = false
                } else {
                    if (binding.layoutKeyboardFake.layoutParams.height != height) {
                        binding.layoutKeyboardFake.layoutParams.height = height
                        binding.layoutKeyboardFake.requestLayout()
                    }
                    binding.layoutKeyboardFake.isVisible = true
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }

    public override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }
}

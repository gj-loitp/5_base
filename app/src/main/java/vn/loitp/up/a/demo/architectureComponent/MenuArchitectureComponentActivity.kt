package vn.loitp.up.a.demo.architectureComponent

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuDemoArchitectureComponentBinding
import vn.loitp.up.a.demo.architectureComponent.coroutine.CoroutineActivityFont
import vn.loitp.up.a.demo.architectureComponent.room.WordActivity
import vn.loitp.up.a.demo.architectureComponent.vm.ViewModelActivity

@LogTag("MenuArchitectureComponentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuArchitectureComponentActivity : BaseActivityFont() {

    private lateinit var binding: AMenuDemoArchitectureComponentBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuDemoArchitectureComponentBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuArchitectureComponentActivity::class.java.simpleName
        }
        binding.btCoroutine.setSafeOnClickListener {
            launchActivity(CoroutineActivityFont::class.java)
        }
        binding.btRoom.setSafeOnClickListener {
            launchActivity(WordActivity::class.java)
        }
        binding.btViewModel.setSafeOnClickListener {
            launchActivity(ViewModelActivity::class.java)
        }
    }
}

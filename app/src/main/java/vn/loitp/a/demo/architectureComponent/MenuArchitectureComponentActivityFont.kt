package vn.loitp.a.demo.architectureComponent

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_demo_architecture_component.*
import vn.loitp.R
import vn.loitp.a.demo.architectureComponent.coroutine.CoroutineActivityFont
import vn.loitp.a.demo.architectureComponent.room.WordActivityFont
import vn.loitp.a.demo.architectureComponent.vm.ViewModelActivityFont

@LogTag("MenuArchitectureComponentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuArchitectureComponentActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_demo_architecture_component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuArchitectureComponentActivityFont::class.java.simpleName
        }
        btCoroutine.setOnClickListener(this)
        btRoom.setOnClickListener(this)
        btViewModel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCoroutine -> intent = Intent(this, CoroutineActivityFont::class.java)
            btRoom -> intent = Intent(this, WordActivityFont::class.java)
            btViewModel -> intent = Intent(this, ViewModelActivityFont::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            this.tranIn()
        }
    }
}

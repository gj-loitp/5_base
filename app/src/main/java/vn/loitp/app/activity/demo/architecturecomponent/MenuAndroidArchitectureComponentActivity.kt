package vn.loitp.app.activity.demo.architecturecomponent

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_demo_architecture_component_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architecturecomponent.coroutine.CoroutineActivity
import vn.loitp.app.activity.demo.architecturecomponent.room.WordActivity
import vn.loitp.app.activity.demo.architecturecomponent.viewmodel.ViewModelActivity

@LogTag("MenuAndroidArchitectureComponentActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAndroidArchitectureComponentActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_architecture_component_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btCoroutine.setOnClickListener(this)
        btRoom.setOnClickListener(this)
        btViewModel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btCoroutine -> intent = Intent(this, CoroutineActivity::class.java)
            btRoom -> intent = Intent(this, WordActivity::class.java)
            btViewModel -> intent = Intent(this, ViewModelActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(this)
        }
    }
}

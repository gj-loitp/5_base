package vn.loitp.app.activity.demo.architecturecomponent

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_demo_architecture_component_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architecturecomponent.coroutine.CoroutineActivity
import vn.loitp.app.activity.demo.architecturecomponent.room.WordActivity
import vn.loitp.app.activity.demo.architecturecomponent.viewmodel.ViewModelActivity

@LayoutId(R.layout.activity_demo_architecture_component_menu)
class MenuAndroidArchitectureComponentActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btCoroutine.setOnClickListener(this)
        btRoom.setOnClickListener(this)
        btViewModel.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
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
            LActivityUtil.tranIn(activity)
        }
    }
}

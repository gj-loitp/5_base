package vn.loitp.app.activity.demo.architecturecomponent

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_menu_architecture_component.*
import loitp.basemaster.R
import vn.loitp.app.activity.demo.architecturecomponent.coroutine.CoroutineActivity
import vn.loitp.app.activity.demo.architecturecomponent.room.WordActivity
import vn.loitp.app.activity.demo.architecturecomponent.viewmodel.ViewModelActivity

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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_architecture_component
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v?.id) {
            R.id.btCoroutine -> intent = Intent(this, CoroutineActivity::class.java)
            R.id.btRoom -> intent = Intent(this, WordActivity::class.java)
            R.id.btViewModel -> intent = Intent(this, ViewModelActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(activity)
        }
    }
}

package vn.loitp.app.activity.demo.firebase.admob

import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import vn.loitp.app.R

@LogTag("FirebaseAdmobActivity2")
@IsFullScreen(false)
class FirebaseAdmobActivity2 : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_firebase_admob2
    }
}

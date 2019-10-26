package vn.loitp.app.activity.database.sharedprefs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
import vn.loitp.app.activity.database.readsqliteasset.ReadSqliteAssetActivity
import vn.loitp.app.activity.database.realm.RealmActivity
import vn.loitp.app.activity.database.sqlite.SqliteActivity

class SharedPrefsActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shared_prefs
    }
}

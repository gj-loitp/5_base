package vn.loitp.app.activity.database

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_database_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.readsqliteasset.ReadSqliteAssetActivity
import vn.loitp.app.activity.database.realm.RealmActivity
import vn.loitp.app.activity.database.room.RoomActivity
import vn.loitp.app.activity.database.sharedprefs.SharedPrefsActivity
import vn.loitp.app.activity.database.sharedprefsencryption.EnctyptionSharedPrefsActivity
import vn.loitp.app.activity.database.sqlite.SqliteActivity
import vn.loitp.app.activity.database.sqliteencryption.SqliteEncryptionActivity
import vn.loitp.app.activity.database.sqlitemultitable.SqliteMultiTableActivity
import vn.loitp.app.activity.database.sqlitemultitableadvance.SqliteMultiTableAdvanceActivity
import vn.loitp.app.activity.demo.architecturecomponent.room.WordActivity

@LayoutId(R.layout.activity_database_menu)
class MenuDatabaseActivity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btSqlite.setOnClickListener(this)
        btRealm.setOnClickListener(this)
        btSqliteAsset.setOnClickListener(this)
        btSharedPrefs.setOnClickListener(this)
        btSharedPrefsEncryption.setOnClickListener(this)
        btSqliteEncryption.setOnClickListener(this)
        btSqliteMultiTable.setOnClickListener(this)
        btSqliteMultiTableAdvance.setOnClickListener(this)
        btRoom.setOnClickListener(this)
        btRoom2.setOnClickListener(this)
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
            btSqlite -> intent = Intent(activity, SqliteActivity::class.java)
            btRealm -> intent = Intent(activity, RealmActivity::class.java)
            btSqliteAsset -> intent = Intent(activity, ReadSqliteAssetActivity::class.java)
            btSharedPrefs -> intent = Intent(activity, SharedPrefsActivity::class.java)
            btSharedPrefsEncryption -> intent = Intent(activity, EnctyptionSharedPrefsActivity::class.java)
            btSqliteEncryption -> intent = Intent(activity, SqliteEncryptionActivity::class.java)
            btSqliteMultiTable -> intent = Intent(activity, SqliteMultiTableActivity::class.java)
            btSqliteMultiTableAdvance -> intent = Intent(activity, SqliteMultiTableAdvanceActivity::class.java)
            btRoom -> intent = Intent(activity, WordActivity::class.java)
            btRoom2 -> intent = Intent(activity, RoomActivity::class.java)
        }
        intent?.let{
            startActivity(it)
            LActivityUtil.tranIn(activity)
        }
    }
}

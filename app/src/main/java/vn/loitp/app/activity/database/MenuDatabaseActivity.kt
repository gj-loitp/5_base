package vn.loitp.app.activity.database

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LogTag
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

@LogTag("MenuDatabaseActivity")
@IsFullScreen(false)
class MenuDatabaseActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_database_menu
    }

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

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btSqlite -> intent = Intent(this, SqliteActivity::class.java)
            btRealm -> intent = Intent(this, RealmActivity::class.java)
            btSqliteAsset -> intent = Intent(this, ReadSqliteAssetActivity::class.java)
            btSharedPrefs -> intent = Intent(this, SharedPrefsActivity::class.java)
            btSharedPrefsEncryption -> intent = Intent(this, EnctyptionSharedPrefsActivity::class.java)
            btSqliteEncryption -> intent = Intent(this, SqliteEncryptionActivity::class.java)
            btSqliteMultiTable -> intent = Intent(this, SqliteMultiTableActivity::class.java)
            btSqliteMultiTableAdvance -> intent = Intent(this, SqliteMultiTableAdvanceActivity::class.java)
            btRoom -> intent = Intent(this, WordActivity::class.java)
            btRoom2 -> intent = Intent(this, RoomActivity::class.java)
        }
        intent?.let{
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}

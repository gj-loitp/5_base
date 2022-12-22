package vn.loitp.app.activity.database

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LActivityUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_database.*
import vn.loitp.R
import vn.loitp.app.activity.database.readSqliteAsset.ReadSqliteAssetActivity
import vn.loitp.app.activity.database.realm.RealmActivity
import vn.loitp.app.activity.database.room.RoomActivity
import vn.loitp.app.activity.database.sharedPrefs.SharedPrefsActivity
import vn.loitp.app.activity.database.sharedPrefsEncryption.EncryptionSharedPrefsActivity
import vn.loitp.app.activity.database.sqlite.SqliteActivity
import vn.loitp.app.activity.database.sqliteEncryption.SqliteEncryptionActivity
import vn.loitp.app.activity.database.sqliteMultiTable.SqliteMultiTableActivity
import vn.loitp.app.activity.database.sqliteMultiTableAdvance.SqliteMultiTableAdvanceActivity
import vn.loitp.app.activity.demo.architectureComponent.room.WordActivity

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

@LogTag("MenuDatabaseActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuDatabaseActivity : BaseFontActivity(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_database
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
            this.tvTitle?.text = MenuDatabaseActivity::class.java.simpleName
        }
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
            btSharedPrefsEncryption ->
                intent =
                    Intent(this, EncryptionSharedPrefsActivity::class.java)
            btSqliteEncryption -> intent = Intent(this, SqliteEncryptionActivity::class.java)
            btSqliteMultiTable -> intent = Intent(this, SqliteMultiTableActivity::class.java)
            btSqliteMultiTableAdvance ->
                intent =
                    Intent(this, SqliteMultiTableAdvanceActivity::class.java)
            btRoom -> intent = Intent(this, WordActivity::class.java)
            btRoom2 -> intent = Intent(this, RoomActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}

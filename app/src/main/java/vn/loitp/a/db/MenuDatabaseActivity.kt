package vn.loitp.a.db

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import kotlinx.android.synthetic.main.a_db_menu.*
import vn.loitp.R
import vn.loitp.a.db.readSqliteAsset.ReadSqliteAssetActivityFont
import vn.loitp.a.db.realm.RealmActivityFont
import vn.loitp.a.db.room.RoomActivityFont
import vn.loitp.a.db.sharedPrefs.SharedPrefsActivityFont
import vn.loitp.a.db.sharedPrefsEncryption.EncryptionSharedPrefsActivityFont
import vn.loitp.a.db.sqlite.SqliteActivityFont
import vn.loitp.a.db.sqliteEncryption.SqliteEncryptionActivityFont
import vn.loitp.a.db.sqliteMultiTable.SqliteMultiTableActivityFont
import vn.loitp.a.db.sqliteMultiTableAdvance.SqliteMultiTableAdvanceActivityFont
import vn.loitp.up.a.demo.architectureComponent.room.WordActivity

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
class MenuDatabaseActivity : BaseActivityFont(), OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_db_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
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
            btSqlite -> intent = Intent(this, SqliteActivityFont::class.java)
            btRealm -> intent = Intent(this, RealmActivityFont::class.java)
            btSqliteAsset -> intent = Intent(this, ReadSqliteAssetActivityFont::class.java)
            btSharedPrefs -> intent = Intent(this, SharedPrefsActivityFont::class.java)
            btSharedPrefsEncryption ->
                intent =
                    Intent(this, EncryptionSharedPrefsActivityFont::class.java)
            btSqliteEncryption -> intent = Intent(this, SqliteEncryptionActivityFont::class.java)
            btSqliteMultiTable -> intent = Intent(this, SqliteMultiTableActivityFont::class.java)
            btSqliteMultiTableAdvance ->
                intent =
                    Intent(this, SqliteMultiTableAdvanceActivityFont::class.java)
            btRoom -> intent = Intent(this, WordActivity::class.java)
            btRoom2 -> intent = Intent(this, RoomActivityFont::class.java)
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}

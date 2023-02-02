package vn.loitp.up.a.db

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
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
import vn.loitp.databinding.ADbMenuBinding
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
class MenuDatabaseActivity : BaseActivityFont() {

    private lateinit var binding: ADbMenuBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ADbMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuDatabaseActivity::class.java.simpleName
        }
        binding.btSqlite.setSafeOnClickListener {
            launchActivity(SqliteActivityFont::class.java)
        }
        binding.btRealm.setSafeOnClickListener {
            launchActivity(RealmActivityFont::class.java)
        }
        binding.btSqliteAsset.setSafeOnClickListener {
            launchActivity(ReadSqliteAssetActivityFont::class.java)
        }
        binding.btSharedPrefs.setSafeOnClickListener {
            launchActivity(SharedPrefsActivityFont::class.java)
        }
        binding.btSharedPrefsEncryption.setSafeOnClickListener {
            launchActivity(
                EncryptionSharedPrefsActivityFont::class.java
            )
        }
        binding.btSqliteEncryption.setSafeOnClickListener {
            launchActivity(
                SqliteEncryptionActivityFont::class.java
            )
        }
        binding.btSqliteMultiTable.setSafeOnClickListener {
            launchActivity(
                SqliteMultiTableActivityFont::class.java
            )
        }
        binding.btSqliteMultiTableAdvance.setSafeOnClickListener {
            launchActivity(
                SqliteMultiTableAdvanceActivityFont::class.java
            )
        }
        binding.btRoom.setSafeOnClickListener {
            launchActivity(WordActivity::class.java)
        }
        binding.btRoom2.setSafeOnClickListener {
            launchActivity(RoomActivityFont::class.java)
        }
    }
}

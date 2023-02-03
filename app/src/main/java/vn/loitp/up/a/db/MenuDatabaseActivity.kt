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
import vn.loitp.databinding.ADbMenuBinding
import vn.loitp.up.a.db.realm.RealmActivity
import vn.loitp.up.a.db.room.RoomActivity
import vn.loitp.up.a.db.sharedPrefs.SharedPrefsActivity
import vn.loitp.up.a.db.sharedPrefsEncryption.EncryptionSharedPrefsActivity
import vn.loitp.up.a.db.sqlite.SqliteActivity
import vn.loitp.up.a.db.sqliteEncryption.SqliteEncryptionActivity
import vn.loitp.up.a.db.sqliteMultiTable.SqliteMultiTableActivity
import vn.loitp.up.a.db.sqliteMultiTableAdvance.SqliteMultiTableAdvanceActivity
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
            launchActivity(SqliteActivity::class.java)
        }
        binding.btRealm.setSafeOnClickListener {
            launchActivity(RealmActivity::class.java)
        }
        binding.btSqliteAsset.setSafeOnClickListener {
            launchActivity(ReadSqliteAssetActivityFont::class.java)
        }
        binding.btSharedPrefs.setSafeOnClickListener {
            launchActivity(SharedPrefsActivity::class.java)
        }
        binding.btSharedPrefsEncryption.setSafeOnClickListener {
            launchActivity(
                EncryptionSharedPrefsActivity::class.java
            )
        }
        binding.btSqliteEncryption.setSafeOnClickListener {
            launchActivity(
                SqliteEncryptionActivity::class.java
            )
        }
        binding.btSqliteMultiTable.setSafeOnClickListener {
            launchActivity(
                SqliteMultiTableActivity::class.java
            )
        }
        binding.btSqliteMultiTableAdvance.setSafeOnClickListener {
            launchActivity(
                SqliteMultiTableAdvanceActivity::class.java
            )
        }
        binding.btRoom.setSafeOnClickListener {
            launchActivity(WordActivity::class.java)
        }
        binding.btRoom2.setSafeOnClickListener {
            launchActivity(RoomActivity::class.java)
        }
    }
}

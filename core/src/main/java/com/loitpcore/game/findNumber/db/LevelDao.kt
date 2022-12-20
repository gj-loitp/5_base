package com.loitpcore.game.findNumber.db

import androidx.room.Dao
import androidx.room.Query
import com.loitp.core.base.BaseDao
import com.loitpcore.game.findNumber.model.Level

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Dao
interface LevelDao : BaseDao<Level> {

    @Query("SELECT * FROM Level")
    fun getListLevel(): List<Level>

//    @Query("SELECT * FROM Level")
//    fun saveListLevel(): List<Level>

    @Query("SELECT * FROM Level WHERE status=:status ORDER BY id")
    fun getFirstLevelOpen(status: Int): Level?
}

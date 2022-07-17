package com.loitpcore.game.findNumber.db

import androidx.room.Dao
import androidx.room.Query
import com.loitpcore.core.base.BaseDao
import com.loitpcore.game.findNumber.model.Level

@Dao
interface LevelDao : BaseDao<Level> {

    @Query("SELECT * FROM Level")
    fun getListLevel(): List<Level>

//    @Query("SELECT * FROM Level")
//    fun saveListLevel(): List<Level>

    @Query("SELECT * FROM Level WHERE status=:status ORDER BY id")
    fun getFirstLevelOpen(status: Int): Level?
}

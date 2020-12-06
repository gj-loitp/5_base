package com.game.findnumber.db

import androidx.room.Dao
import androidx.room.Query
import com.core.base.BaseDao
import com.game.findnumber.model.Level

@Dao
interface LevelDao : BaseDao<Level> {

    @Query("SELECT * FROM Level")
    fun getListLevel(): List<Level>

//    @Query("SELECT * FROM Level WHERE id=:id")
//    fun find(id: String?): Level?

}

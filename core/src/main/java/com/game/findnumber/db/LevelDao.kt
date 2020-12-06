package com.game.findnumber.db

import androidx.room.Dao
import com.core.base.BaseDao
import com.game.findnumber.model.Level

@Dao
interface LevelDao : BaseDao<Level> {

//    @Query("SELECT * FROM Level WHERE isFavorites==1 AND title LIKE '%' || :currentKeyword || '%'")
//    fun getListGirlPage(currentKeyword: String): List<Level>

//    @Query("SELECT * FROM Level WHERE id=:id")
//    fun find(id: String?): Level?
}

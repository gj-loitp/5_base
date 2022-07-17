package com.loitpcore.core.helper.ttt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loitpcore.core.base.BaseDao
import com.loitpcore.core.helper.ttt.model.comic.Comic

@Dao
interface TTTDao : BaseDao<Comic> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListComic(list: List<Comic>)

    @Query("SELECT * FROM Comic WHERE type=:type")
    fun getListComic(type: String?): List<Comic>

    @Query("SELECT * FROM Comic WHERE isFav=1")
    fun getListComicFav(): List<Comic>

//    @Query("DELETE FROM Comic")
//    suspend fun deleteAll()

//    @Query("SELECT * FROM Comic WHERE url=:url")
//    fun find(url: String): Comic?
}

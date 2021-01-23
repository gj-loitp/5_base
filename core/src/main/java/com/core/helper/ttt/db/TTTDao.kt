package com.core.helper.ttt.db

import androidx.room.Dao
import androidx.room.Query
import com.core.base.BaseDao
import com.core.helper.ttt.model.comic.Comic

@Dao
interface TTTDao : BaseDao<Comic> {

    @Query("SELECT * FROM Comic")
    fun getListComic(): List<Comic>

    @Query("DELETE FROM Comic")
    suspend fun deleteAll()

    @Query("SELECT * FROM Comic WHERE url=:url")
    fun find(url: String): Comic?
}

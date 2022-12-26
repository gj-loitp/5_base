package com.loitp.core.helper.ttt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.loitp.core.base.BaseDao
import com.loitp.core.helper.ttt.model.comic.Comic

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
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

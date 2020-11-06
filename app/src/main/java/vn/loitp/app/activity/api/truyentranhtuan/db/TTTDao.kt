package vn.loitp.app.activity.api.truyentranhtuan.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.core.base.BaseDao
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import vn.loitp.app.activity.database.room.model.FloorPlan

@Dao
interface TTTDao : BaseDao<Comic> {

    @Query("SELECT * FROM Comic")
    fun getListComic(): List<Comic>

    @Query("DELETE FROM Comic")
    suspend fun deleteAll()

    @Query("SELECT * FROM Comic WHERE url=:url")
    fun find(url: String): Comic?
}

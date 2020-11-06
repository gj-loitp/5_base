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

//    @Query("SELECT * FROM floorPlan LIMIT :fromIndex,:offset")
//    fun getListFloorPlanByIndex(fromIndex: Int, offset: Int): List<FloorPlan>
//
//    @Insert
//    fun insertListFloorPlan(list: ArrayList<FloorPlan>)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertListFloorPlanConflict(list: ArrayList<FloorPlan>)
//
//    @Query("DELETE FROM floorPlan")
//    suspend fun deleteAll()
//
//    @Query("SELECT * FROM floorPlan WHERE id=:id")
//    fun find(id: String): FloorPlan?
}

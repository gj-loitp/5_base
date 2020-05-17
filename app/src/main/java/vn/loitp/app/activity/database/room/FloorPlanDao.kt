package vn.loitp.app.activity.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FloorPlanDao {

    @Query("Select * from floorPlan")
    fun getAllFloorPlan(): List<FloorPlan>

    @Insert
    fun insertListFloorPlan(list: List<FloorPlan>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListFloorPlanConflict(list: List<FloorPlan>)

}

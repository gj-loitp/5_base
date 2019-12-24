package vn.loitp.app.activity.demo.architecturecomponent.room.dao

import androidx.room.*

/**
 * Created by Loitp on 05,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
@Dao
interface BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T): Long

    @Delete
    suspend fun delete(type: T)

    @Update
    suspend fun update(type: T)
}
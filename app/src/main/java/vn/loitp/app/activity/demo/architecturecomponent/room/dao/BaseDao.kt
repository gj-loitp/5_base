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
    fun insert(t: T): Long

    @Delete
    fun delete(type: T)

    @Update
    fun update(type: T)
}
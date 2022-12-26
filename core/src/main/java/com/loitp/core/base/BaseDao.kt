package com.loitp.core.base

import androidx.room.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
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

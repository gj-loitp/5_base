package com.core.base

import androidx.room.* // ktlint-disable no-wildcard-imports

@Dao
interface BaseDao<in T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(t: T): Long

    @Delete
    suspend fun delete(type: T)

    @Update
    suspend fun update(type: T)
}

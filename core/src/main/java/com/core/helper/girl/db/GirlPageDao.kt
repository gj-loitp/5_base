package com.core.helper.girl.db

import androidx.room.Dao
import androidx.room.Query
import com.core.base.BaseDao
import com.core.helper.girl.model.GirlPage

@Dao
interface GirlPageDao : BaseDao<GirlPage> {

    @Query("SELECT * FROM GirlPage")
    fun getListGirlPage(): List<GirlPage>

    @Query("SELECT * FROM GirlPage WHERE id=:id")
    fun find(id: String?): GirlPage?
}

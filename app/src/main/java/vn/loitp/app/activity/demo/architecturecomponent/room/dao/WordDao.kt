package vn.loitp.app.activity.demo.architecturecomponent.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word

@Dao
interface WordDao : BaseDao<Word> {

    @Query("SELECT * from ${Word.TABLE_WORD} ORDER BY ${Word.COL_WORD} ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Query("DELETE FROM ${Word.TABLE_WORD}")
    suspend fun deleteAll()
}

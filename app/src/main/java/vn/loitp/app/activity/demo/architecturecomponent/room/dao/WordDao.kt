package vn.loitp.app.activity.demo.architecturecomponent.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word

@Dao
interface WordDao : BaseDao<Word> {
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}

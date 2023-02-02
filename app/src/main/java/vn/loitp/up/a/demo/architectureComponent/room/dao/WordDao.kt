package vn.loitp.up.a.demo.architectureComponent.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.loitp.core.base.BaseDao
import vn.loitp.up.a.demo.architectureComponent.room.md.Word

@Dao
interface WordDao : BaseDao<Word> {

    // @Query("SELECT * from ${Word.TABLE_WORD} ORDER BY ${Word.COL_WORD} ASC")
    @Query("SELECT * FROM ${Word.TABLE_WORD} ORDER BY ${Word.COL_ID} ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Query("DELETE FROM ${Word.TABLE_WORD}")
    suspend fun deleteAll()

    @Query("SELECT * FROM ${Word.TABLE_WORD} WHERE ${Word.COL_ID}=:id")
    fun findWord(id: String): LiveData<Word>
}

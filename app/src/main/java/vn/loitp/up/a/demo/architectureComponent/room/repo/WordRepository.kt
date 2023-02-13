package vn.loitp.up.a.demo.architectureComponent.room.repo

import androidx.lifecycle.LiveData
import vn.loitp.up.a.demo.architectureComponent.room.dao.WordDao
import vn.loitp.up.a.demo.architectureComponent.room.md.Word

class WordRepository(private val wordDao: WordDao) {

    fun getAlphabetizedWords(): LiveData<List<Word>> {
        return wordDao.getAlphabetizedWords()
    }

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    suspend fun deleteAll() {
        wordDao.deleteAll()
    }

    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }

    suspend fun update(word: Word) {
        wordDao.update(word)
    }

    fun findWord(id: String): LiveData<Word> {
        return wordDao.findWord(id)
    }
}

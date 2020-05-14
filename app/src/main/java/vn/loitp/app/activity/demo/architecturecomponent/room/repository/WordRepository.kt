package vn.loitp.app.activity.demo.architecturecomponent.room.repository

import vn.loitp.app.activity.demo.architecturecomponent.room.dao.WordDao
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word

class WordRepository(private val wordDao: WordDao) {

    val listWord = wordDao.getAlphabetizedWords()

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

//    fun findWord(id: String): LiveData<Word> {
//        return wordDao.findWord(id)
//    }
}
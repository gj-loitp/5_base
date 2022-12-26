package vn.loitp.a.demo.architectureComponent.room.md

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.loitp.a.demo.architectureComponent.room.db.WordRoomDatabase
import vn.loitp.a.demo.architectureComponent.room.repo.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WordRepository
    var listWord: LiveData<List<Word>>? = null
    var wordFind: LiveData<Word>? = null

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)

        getAlphabetizedWords()
    }

    private fun getAlphabetizedWords() {
        listWord = repository.getAlphabetizedWords()
    }

    fun genFirstData() {
        // Log.d(TAG, "genFirstData " + LApplication.gson.toJson(allWords.value))
        if (listWord?.value.isNullOrEmpty()) {
            val word = Word()
            word.word = "Loitp (First data if list null)"
            insert(word)
        }
    }

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
    }

    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
    }

    fun findWord(id: String) = viewModelScope.launch {
        wordFind = repository.findWord(id)
    }
}

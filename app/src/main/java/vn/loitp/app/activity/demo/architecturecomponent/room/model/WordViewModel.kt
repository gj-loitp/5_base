package vn.loitp.app.activity.demo.architecturecomponent.room.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.loitp.app.activity.demo.architecturecomponent.room.db.WordRoomDatabase
import vn.loitp.app.activity.demo.architecturecomponent.room.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository
    val allWords: LiveData<List<Word>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        allWords = repository.allWords
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
}

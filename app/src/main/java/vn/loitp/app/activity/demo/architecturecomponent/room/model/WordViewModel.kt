package vn.loitp.app.activity.demo.architecturecomponent.room.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vn.loitp.app.activity.demo.architecturecomponent.room.db.WordRoomDatabase
import vn.loitp.app.activity.demo.architecturecomponent.room.repository.WordRepository
import vn.loitp.app.app.LApplication

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val logTag = javaClass.simpleName
    private val repository: WordRepository
    var listWord: LiveData<List<Word>>? = null
    var wordFind: LiveData<Word>? = null

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)

        getAlphabetizedWords()
    }

    fun getAlphabetizedWords() {
        listWord = repository.getAlphabetizedWords()
    }

    fun genFirstData() {
        //Log.d(TAG, "genFirstData " + LApplication.gson.toJson(allWords.value))
        if (listWord?.value.isNullOrEmpty()) {
            val word = Word()
            word.word = "Loitp (First data if list null)"
            insert(word)
        }
    }

    fun insert(word: Word) = viewModelScope.launch {
        Log.d(logTag, "insert " + LApplication.gson.toJson(word))
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
        Log.d(logTag, "findWord id $id")
        wordFind = repository.findWord(id)
    }
}

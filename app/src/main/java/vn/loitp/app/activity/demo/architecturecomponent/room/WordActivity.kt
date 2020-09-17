package vn.loitp.app.activity.demo.architecturecomponent.room

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_demo_database_room_work.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word
import vn.loitp.app.activity.demo.architecturecomponent.room.model.WordViewModel
import vn.loitp.app.app.LApplication

//https://codinginfinite.com/android-room-tutorial-persistence/
//https://codinginfinite.com/android-room-persistent-rxjava/
//https://codinginfinite.com/android-room-persistence-livedata-example/

@LayoutId(R.layout.activity_demo_database_room_work)
class WordActivity : BaseFontActivity() {
    private var wordViewModel: WordViewModel? = null
    private var wordListAdapter: WordListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupViewModels()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private fun setupViews() {
        wordListAdapter = WordListAdapter(object : WordListAdapter.Callback {
            override fun onUpdate(word: Word) {
                handleUpdate(word = word)
            }

            override fun onDelete(word: Word) {
                handleDelete(word)
            }
        })
        recyclerView.adapter = wordListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        btAdd.setSafeOnClickListener {
            handleInsert()
        }
        btDeleteAll.setSafeOnClickListener {
            handleDeleteAll()
        }
        btFindWord.setSafeOnClickListener {
            handleFindWord()
        }
    }

    private fun setupViewModels() {
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel?.let { vm ->
            vm.listWord?.observe(this, Observer { allWords ->
                logD("allWords observe " + LApplication.gson.toJson(allWords))
                allWords?.let {
                    wordListAdapter?.setWords(it)
                }
                genFirstData()
            })
            vm.wordFind?.observe(this, Observer {
                logD("wordFind observe " + LApplication.gson.toJson(it))
            })
        }
    }

    private fun handleUpdate(word: Word) {
        word.word = "Update " + System.currentTimeMillis()
        wordViewModel?.update(word)
    }

    private fun handleDelete(word: Word) {
        wordViewModel?.delete(word)
    }

    private fun handleDeleteAll() {
        wordViewModel?.deleteAll()
    }

    private fun handleInsert() {
        val word = Word()
        word.word = "Add " + System.currentTimeMillis()
        wordViewModel?.insert(word)
    }

    private var isGenFirstDataDone = false
    private fun genFirstData() {
        if (!isGenFirstDataDone) {
            showShort("genFirstData")
            wordViewModel?.genFirstData()
            isGenFirstDataDone = true
        }
    }

    private fun handleFindWord() {
        wordViewModel?.findWord(id = "1")
    }
}

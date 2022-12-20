package vn.loitp.app.activity.demo.architectureComponent.room

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_demo_database_room_work.*
import vn.loitp.app.R
import vn.loitp.app.activity.demo.architectureComponent.room.model.Word
import vn.loitp.app.activity.demo.architectureComponent.room.model.WordViewModel

// https://codinginfinite.com/android-room-tutorial-persistence/
// https://codinginfinite.com/android-room-persistent-rxjava/
// https://codinginfinite.com/android-room-persistence-livedata-example/

@LogTag("WordActivity")
@IsFullScreen(false)
class WordActivity : BaseFontActivity() {
    private var wordViewModel: WordViewModel? = null
    private var wordListAdapter: WordListAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_database_room_work
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = WordActivity::class.java.simpleName
        }
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
        wordViewModel = ViewModelProvider(this)[WordViewModel::class.java]
        wordViewModel?.let { vm ->
            vm.listWord?.observe(
                this
            ) { allWords ->
                logD("allWords observe " + BaseApplication.gson.toJson(allWords))
                allWords?.let {
                    wordListAdapter?.setWords(it)
                }
                genFirstData()
            }
            vm.wordFind?.observe(
                this
            ) {
                logD("wordFind observe " + BaseApplication.gson.toJson(it))
            }
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
            showShortInformation("genFirstData")
            wordViewModel?.genFirstData()
            isGenFirstDataDone = true
        }
    }

    private fun handleFindWord() {
        wordViewModel?.findWord(id = "1")
    }
}

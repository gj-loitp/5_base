package vn.loitp.app.activity.demo.architecturecomponent.room.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_room_work.*
import loitp.basemaster.R
import vn.loitp.app.activity.demo.architecturecomponent.room.adapter.WordListAdapter
import vn.loitp.app.activity.demo.architecturecomponent.room.model.Word
import vn.loitp.app.activity.demo.architecturecomponent.room.model.WordViewModel

//https://codinginfinite.com/android-room-tutorial-persistence/
//https://codinginfinite.com/android-room-persistent-rxjava/
//https://codinginfinite.com/android-room-persistence-livedata-example/
class WordActivity : BaseFontActivity() {
    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = WordListAdapter(object : WordListAdapter.Callback {
            override fun onUpdate(word: Word) {

            }

            override fun onDelete(word: Word) {

            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            words?.let { adapter.setWords(it) }
        })

        btAdd.setOnClickListener {
            val word = Word()
            word.word = "Add " + System.currentTimeMillis()
            wordViewModel.insert(word)
        }

        btClearAll.setOnClickListener {

        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_room_work
    }
}

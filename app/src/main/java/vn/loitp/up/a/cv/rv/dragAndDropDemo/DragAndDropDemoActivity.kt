package vn.loitp.up.a.cv.rv.dragAndDropDemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.flexbox.*
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ARvDragAndDropDemoBinding
import vn.loitp.up.a.cv.rv.dragAndDropDemo.adt.SentenceAdapter
import vn.loitp.up.a.cv.rv.dragAndDropDemo.adt.WordsAdapter
import vn.loitp.up.a.cv.rv.dragAndDropDemo.callback.DropListener

@LogTag("DragAndDropDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class DragAndDropDemoActivity : BaseActivityFont() {

    // values of the draggable views (usually this should come from a data source)
    private val words = mutableListOf("world", "a", "!", "What", "wonderful")

    // last selected word
    private var selectedWord = ""
    private lateinit var binding: ARvDragAndDropDemoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARvDragAndDropDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/robertlevonyan/drag-and-drop-demo"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DragAndDropDemoActivity::class.java.simpleName
        }

        val sentenceAdapter = SentenceAdapter()
        val wordsAdapter = WordsAdapter {
            selectedWord = it
        }.apply {
            submitList(words)
        }

//        rvSentence.layoutManager =
//            LinearLayoutManager(
//                /* context = */ this,
//                /* orientation = */ LinearLayoutManager.HORIZONTAL,
//                /* reverseLayout = */ false
//            )
        binding.rvSentence.layoutManager = FlexboxLayoutManager(
            /* context = */ this,
            /* flexDirection = */ FlexDirection.ROW,
            /* flexWrap = */ FlexWrap.NOWRAP
        ).apply {
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        binding.rvSentence.adapter = sentenceAdapter

        binding.rvSentence.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                sentenceAdapter.addItem(selectedWord)
            }
        )

        binding.rvWords.layoutManager = FlexboxLayoutManager(
            /* context = */ this,
            /* flexDirection = */ FlexDirection.ROW,
            /* flexWrap = */ FlexWrap.WRAP
        ).apply {
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        binding.rvWords.adapter = wordsAdapter
    }
}

package vn.loitp.a.cv.rv.dragAndDropDemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.flexbox.*
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_rv_drag_and_drop_demo.*
import vn.loitp.R
import vn.loitp.a.cv.rv.dragAndDropDemo.adt.SentenceAdapter
import vn.loitp.a.cv.rv.dragAndDropDemo.adt.WordsAdapter
import vn.loitp.a.cv.rv.dragAndDropDemo.callback.DropListener

@LogTag("DragAndDropDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class DragAndDropDemoActivityFont : BaseActivityFont() {

    // values of the draggable views (usually this should come from a data source)
    private val words = mutableListOf("world", "a", "!", "What", "wonderful")

    // last selected word
    private var selectedWord = ""

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_drag_and_drop_demo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/robertlevonyan/drag-and-drop-demo"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = DragAndDropDemoActivityFont::class.java.simpleName
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
        rvSentence.layoutManager = FlexboxLayoutManager(
            /* context = */ this,
            /* flexDirection = */ FlexDirection.ROW,
            /* flexWrap = */ FlexWrap.NOWRAP
        ).apply {
            justifyContent = JustifyContent.FLEX_START
            alignItems = AlignItems.FLEX_START
        }
        rvSentence.adapter = sentenceAdapter

        rvSentence.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                sentenceAdapter.addItem(selectedWord)
            }
        )

        rvWords.layoutManager = FlexboxLayoutManager(
            /* context = */ this,
            /* flexDirection = */ FlexDirection.ROW,
            /* flexWrap = */ FlexWrap.WRAP
        ).apply {
            justifyContent = JustifyContent.SPACE_EVENLY
            alignItems = AlignItems.CENTER
        }

        rvWords.adapter = wordsAdapter
    }
}

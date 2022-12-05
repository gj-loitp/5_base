package vn.loitp.app.activity.customviews.recyclerview.dragAndDropDemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.*
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_drag_and_drop_demo.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.dragAndDropDemo.adapter.SentenceAdapter
import vn.loitp.app.activity.customviews.recyclerview.dragAndDropDemo.adapter.WordsAdapter
import vn.loitp.app.activity.customviews.recyclerview.dragAndDropDemo.callback.DropListener

@LogTag("DragAndDropDemoActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class DragAndDropDemoActivity : BaseFontActivity() {

    // values of the draggable views (usually this should come from a data source)
    private val words = mutableListOf("world", "a", "!", "What", "wonderful")

    // last selected word
    private var selectedWord = ""

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_drag_and_drop_demo
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
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = DragAndDropDemoActivity::class.java.simpleName
        }

        val sentenceAdapter = SentenceAdapter()
        val wordsAdapter = WordsAdapter {
            selectedWord = it
        }.apply {
            submitList(words)
        }

        rvSentence.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSentence.adapter = sentenceAdapter

        rvSentence.setOnDragListener(
            DropListener {
                wordsAdapter.removeItem(selectedWord)
                sentenceAdapter.addItem(selectedWord)
            }
        )

        rvWords.layoutManager =
            FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP).apply {
                justifyContent = JustifyContent.SPACE_EVENLY
                alignItems = AlignItems.CENTER
            }

        rvWords.adapter = wordsAdapter
    }
}

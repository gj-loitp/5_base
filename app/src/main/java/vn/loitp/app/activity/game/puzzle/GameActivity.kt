package vn.loitp.app.activity.game.puzzle

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Size
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.ViewModelProviders
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import vn.loitp.app.R
import vn.loitp.app.activity.game.puzzle.ui.boardoptions.BoardOptionsViewModel
import vn.loitp.app.activity.game.puzzle.ui.boardoptions.BoardTitledSize
import vn.loitp.app.activity.game.puzzle.ui.game.GameBoard

data class BoardActivityParams(
    val bitmap: Bitmap, val size: BoardTitledSize
)

@LogTag("BoardOptionsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class GameActivity : BaseFontActivity() {
    companion object {
        lateinit var initialConfig: BoardActivityParams
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_game_puzzle
    }

    private val viewModel: BoardOptionsViewModel by lazy {
        ViewModelProviders.of(this)[BoardOptionsViewModel::class.java]
    }

    private fun mountBoard() {
        val board = findViewById<GameBoard>(R.id.boardView)

        viewModel.boardSize.observe(this) {
            it?.let {
                board.resize(
                    Size(it.width, it.height), viewModel.boardImage.value
                )
            }
        }

        findViewById<Button>(R.id.shuffle).setOnClickListener {
            board.shuffle()
        }

        findViewById<Button>(R.id.reset).setOnClickListener {
            board.shuffle(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.apply {
            boardSize.value = initialConfig.size
            boardImage.value = initialConfig.bitmap
        }

        super.onCreate(savedInstanceState)

        setSupportActionBar(findViewById(R.id.tbBoardOptions))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mountBoard()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

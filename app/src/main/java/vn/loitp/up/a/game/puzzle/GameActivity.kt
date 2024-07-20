package vn.loitp.up.a.game.puzzle

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Size
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.ActivityGamePuzzleBinding
import vn.loitp.up.a.game.puzzle.ui.options.BoardOptionsViewModel
import vn.loitp.up.a.game.puzzle.ui.options.BoardTitledSize

data class BoardActivityParams(
    val bitmap: Bitmap, val size: BoardTitledSize
)

@LogTag("BoardOptionsActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class GameActivityFont : BaseActivityFont() {
    companion object {
        lateinit var initialConfig: BoardActivityParams
    }

    private lateinit var binding: ActivityGamePuzzleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    private val viewModel: BoardOptionsViewModel by lazy {
        ViewModelProviders.of(this)[BoardOptionsViewModel::class.java]
    }

    private fun mountBoard() {
        viewModel.boardSize.observe(this) {
            it?.let {
                binding.boardView.resize(
                    Size(it.width, it.height), viewModel.boardImage.value
                )
            }
        }

        binding.shuffle.setOnClickListener {
            binding.boardView.shuffle()
        }

        binding.reset.setOnClickListener {
            binding.boardView.shuffle(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.apply {
            boardSize.value = initialConfig.size
            boardImage.value = initialConfig.bitmap
        }

        super.onCreate(savedInstanceState)

        binding = ActivityGamePuzzleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.tbBoardOptions))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mountBoard()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBaseBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

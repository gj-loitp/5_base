package vn.loitp.app.activity.game.puzzle.ui.boardoptions

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.loitp.app.R
import java.io.Serializable

typealias TitledResourcePair = Pair<Int, String>

data class BoardTitledSize(val width: Int, val height: Int) : Serializable {
    override fun toString(): String {
        return "$width x $height"
    }
}

class BoardOptionsViewModel : ViewModel() {
    companion object {
        val PREDEFINED_BOARD_SIZE = arrayOf(
            BoardTitledSize(3, 3),
            BoardTitledSize(4, 4),
            BoardTitledSize(5, 5),
            BoardTitledSize(6, 6),
            BoardTitledSize(7, 7),
            BoardTitledSize(8, 8)
        )

        val PREDEFINED_IMAGES: Array<TitledResourcePair> = arrayOf(
            TitledResourcePair(R.drawable.loitp, "loitp"),
            TitledResourcePair(R.drawable.logo, "logo"),
            TitledResourcePair(R.drawable.iv, "iv"),

            TitledResourcePair(R.drawable.loitp, "loitp"),
            TitledResourcePair(R.drawable.logo, "logo"),
            TitledResourcePair(R.drawable.iv, "iv"),

            TitledResourcePair(R.drawable.loitp, "loitp"),
            TitledResourcePair(R.drawable.logo, "logo"),
            TitledResourcePair(R.drawable.iv, "iv"),

            TitledResourcePair(R.drawable.loitp, "loitp"),
            TitledResourcePair(R.drawable.logo, "logo"),
            TitledResourcePair(R.drawable.iv, "iv"),
        )
    }

    val boardSize = MutableLiveData<BoardTitledSize>()
    val boardImage = MutableLiveData<Bitmap>()
}

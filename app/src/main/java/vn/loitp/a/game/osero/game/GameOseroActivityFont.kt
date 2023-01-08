package vn.loitp.a.game.osero.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_osero_game.*
import vn.loitp.R
import vn.loitp.a.game.osero.md.Place
import vn.loitp.a.game.osero.md.Stone
import vn.loitp.a.game.osero.md.ai.AINone
import vn.loitp.a.game.osero.md.ai.OseroAI

@LogTag("GameOseroActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class GameOseroActivityFont : BaseActivityFont(), GameView {

    companion object {
        const val EXTRA_NAME_AI = "extra_ai"

        fun createIntent(context: Context, ai: OseroAI = AINone()): Intent {
            val intent = Intent(context, GameOseroActivityFont::class.java)
            intent.putExtra(EXTRA_NAME_AI, ai)
            return intent
        }
    }

    private lateinit var placeList: List<List<ImageView>>
    private val presenter = GamePresenter()
    private val boardSize = presenter.boardSize

    override fun setLayoutResourceId(): Int {
        return R.layout.a_osero_game
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("InflateParams")
    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GameOseroActivityFont::class.java.simpleName
        }
        // 二次元配列にマッピングしながらGridLayoutにマスを設定
        placeList = arrayOfNulls<List<ImageView>>(boardSize)
            .mapIndexed { x, _ ->
                arrayOfNulls<ImageView>(boardSize).mapIndexed { y, _ ->
                    val place = layoutInflater.inflate(R.layout.item_osero_grid_place, null)
                    place.setOnClickListener { presenter.onClickPlace(x, y) }
                    gamePlacesGrid.addView(place)
                    place.findViewById(R.id.gamePlaceImageView) as ImageView
                }
            }
        //TODO fix
        val ai = intent.getSerializableExtra(EXTRA_NAME_AI) as? OseroAI ?: AINone()
        presenter.onCreate(this, ai)
    }

    override fun putStone(place: Place) {
        val imageRes = when (place.stone) {
            Stone.BLACK -> R.drawable.black_stone
            Stone.WHITE -> R.drawable.white_stone
            Stone.NONE -> throw IllegalArgumentException()
        }
        placeList[place.x][place.y].setImageResource(imageRes)
    }

    override fun setCurrentPlayerText(player: Stone) {
        val color = when (player) {
            Stone.BLACK -> "Black"
            Stone.WHITE -> "White"
            Stone.NONE -> throw IllegalArgumentException()
        }
        gameCurrentPlayerText.text = getString(R.string.textGameCurrentPlayer, color)
    }

    override fun showWinner(
        player: Stone,
        blackCount: Int,
        whiteCount: Int
    ) {
        val color = when (player) {
            Stone.BLACK -> "Black"
            Stone.WHITE -> "White"
            Stone.NONE -> throw IllegalArgumentException()
        }
        Toast.makeText(
            this,
            getString(R.string.textGameWinner, blackCount, whiteCount, color),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun finishGame() {
        finish()
    }

    override fun markCanPutPlaces(places: List<Place>) {
        places.forEach {
            placeList[it.x][it.y].setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.lightGreen
                )
            )
        }
    }

    override fun clearAllMarkPlaces() {
        placeList.flatten()
            .forEach { it.setBackgroundColor(ContextCompat.getColor(this, R.color.green)) }
    }
}

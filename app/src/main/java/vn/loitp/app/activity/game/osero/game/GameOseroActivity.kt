package vn.loitp.app.activity.game.osero.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_game_osero.*
import vn.loitp.app.R
import vn.loitp.app.activity.game.osero.model.Place
import vn.loitp.app.activity.game.osero.model.Stone
import vn.loitp.app.activity.game.osero.model.ai.AINone
import vn.loitp.app.activity.game.osero.model.ai.OseroAI

@LogTag("GameOseroActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class GameOseroActivity : BaseFontActivity(), GameView {

    companion object {
        const val EXTRA_NAME_AI = "extra_ai"

        fun createIntent(context: Context, ai: OseroAI = AINone()): Intent {
            val intent = Intent(context, GameOseroActivity::class.java)
            intent.putExtra(EXTRA_NAME_AI, ai)
            return intent
        }
    }

    private lateinit var placeList: List<List<ImageView>>
    private val presenter = GamePresenter()
    private val boardSize = presenter.boardSize

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_game_osero
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("InflateParams")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = GameOseroActivity::class.java.simpleName
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

    override fun showWinner(player: Stone, blackCount: Int, whiteCount: Int) {
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

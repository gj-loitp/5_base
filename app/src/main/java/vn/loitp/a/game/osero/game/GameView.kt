package vn.loitp.a.game.osero.game

import vn.loitp.a.game.osero.md.Place
import vn.loitp.a.game.osero.md.Stone

interface GameView {
    fun putStone(place: Place)
    fun setCurrentPlayerText(player: Stone)
    fun showWinner(
        player: Stone,
        blackCount: Int,
        whiteCount: Int
    )

    fun finishGame()
    fun markCanPutPlaces(places: List<Place>)
    fun clearAllMarkPlaces()
}

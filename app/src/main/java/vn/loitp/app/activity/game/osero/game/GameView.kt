package vn.loitp.app.activity.game.osero.game

import vn.loitp.app.activity.game.osero.model.Place
import vn.loitp.app.activity.game.osero.model.Stone

interface GameView {
    fun putStone(place: Place)
    fun setCurrentPlayerText(player: Stone)
    fun showWinner(player: Stone, blackCount: Int, whiteCount: Int)
    fun finishGame()
    fun markCanPutPlaces(places: List<Place>)
    fun clearAllMarkPlaces()
}

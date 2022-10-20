package vn.loitp.app.activity.game.osero.game

import vn.loitp.app.activity.game.osero.model.Place
import vn.loitp.app.activity.game.osero.model.Stone

interface GameView {
    open fun putStone(place: Place)
    open fun setCurrentPlayerText(player: Stone)
    open fun showWinner(player: Stone, blackCount: Int, whiteCount: Int)
    open fun finishGame()
    open fun markCanPutPlaces(places: List<Place>)
    open fun clearAllMarkPlaces()
}
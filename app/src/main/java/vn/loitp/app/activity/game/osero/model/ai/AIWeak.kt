package vn.loitp.app.activity.game.osero.model.ai

import vn.loitp.app.activity.game.osero.model.OseroGame
import vn.loitp.app.activity.game.osero.model.Place
import vn.loitp.app.activity.game.osero.model.Stone

/**
 * 先読みせず一番多く石が取れる手を選ぶ
 */
class AIWeak : OseroAI {
    override fun computeNext(game: OseroGame, color: Stone): Place {
        return game.boardStatus.flatMap { it }
            .filter { game.canPut(Place(it.x, it.y, color)) }
            .maxBy { game.getCanChangePlaces(it).count() }!!
    }
}
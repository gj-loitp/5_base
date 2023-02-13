package vn.loitp.up.a.game.osero.md.ai

import vn.loitp.up.a.game.osero.md.OseroGame
import vn.loitp.up.a.game.osero.md.Place
import vn.loitp.up.a.game.osero.md.Stone

/**
 * 先読みせず一番多く石が取れる手を選ぶ
 */
class AIWeak : OseroAI {
    override fun computeNext(
        game: OseroGame,
        color: Stone
    ): Place {
        return game.boardStatus.flatten()
            .filter { game.canPut(Place(it.x, it.y, color)) }
            .maxBy { game.getCanChangePlaces(it).count() }
    }
}

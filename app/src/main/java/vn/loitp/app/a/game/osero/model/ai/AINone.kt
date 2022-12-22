package vn.loitp.app.a.game.osero.model.ai

import vn.loitp.app.a.game.osero.model.OseroGame
import vn.loitp.app.a.game.osero.model.Place
import vn.loitp.app.a.game.osero.model.Stone

/**
 * AIを使用しない場合
 */
class AINone : OseroAI {

    override fun computeNext(game: OseroGame, color: Stone): Place {
        throw IllegalAccessException()
    }

}

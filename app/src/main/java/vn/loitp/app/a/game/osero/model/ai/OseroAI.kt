package vn.loitp.app.a.game.osero.model.ai

import vn.loitp.app.a.game.osero.model.OseroGame
import vn.loitp.app.a.game.osero.model.Place
import vn.loitp.app.a.game.osero.model.Stone
import java.io.Serializable

interface OseroAI : Serializable {

    fun computeNext(game: OseroGame, color: Stone): Place
}

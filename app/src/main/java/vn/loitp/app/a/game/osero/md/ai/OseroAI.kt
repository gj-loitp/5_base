package vn.loitp.app.a.game.osero.md.ai

import vn.loitp.app.a.game.osero.md.OseroGame
import vn.loitp.app.a.game.osero.md.Place
import vn.loitp.app.a.game.osero.md.Stone
import java.io.Serializable

interface OseroAI : Serializable {

    fun computeNext(game: OseroGame, color: Stone): Place
}

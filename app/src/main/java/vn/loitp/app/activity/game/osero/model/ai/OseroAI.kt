package vn.loitp.app.activity.game.osero.model.ai

import vn.loitp.app.activity.game.osero.model.OseroGame
import vn.loitp.app.activity.game.osero.model.Place
import vn.loitp.app.activity.game.osero.model.Stone
import java.io.Serializable

interface OseroAI : Serializable {

    fun computeNext(game: OseroGame, color: Stone): Place
}

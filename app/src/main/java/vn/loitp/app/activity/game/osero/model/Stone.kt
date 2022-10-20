package vn.loitp.app.activity.game.osero.model

/**
 * 石の状態列挙
 */
enum class Stone {

    BLACK, WHITE, NONE;

    fun other() = if (this == BLACK) WHITE else BLACK
}

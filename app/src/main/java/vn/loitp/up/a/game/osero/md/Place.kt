package vn.loitp.up.a.game.osero.md

/**
 * マスの座標と状態を保持する
 */
data class Place(
    val x: Int,
    val y: Int,
    var stone: Stone,
) {

    override fun equals(other: Any?): Boolean {
        val mOther = other as? Place ?: return false
        return x == mOther.x && y == mOther.y && stone == mOther.stone
    }

    override fun hashCode(): Int {
        var result = x
        result += 31 * result + y
        result += 31 * result + stone.hashCode()
        return result
    }
}

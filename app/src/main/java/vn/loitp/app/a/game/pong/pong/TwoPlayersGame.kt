package vn.loitp.app.a.game.pong.pong

class TwoPlayersGame(
    paddleA: PaddleAbstract,
    paddleB: PaddleAbstract,
    private val ball: Ball,
    difficulty: Difficulty
) : GameAbstract(paddleA, paddleB, ball, difficulty) {

    var pointsA: Int = 0
    var pointsB: Int = 0

    override fun referee(width: Int): Boolean {
        return when {
            ball.ballX < 0f -> {
                pointsB++
                true
            }
            ball.ballX + ball.size > width -> {
                pointsA++
                true
            }
            else -> false
        }
    }

    override fun bounce() {
        checkBounce()
    }

}

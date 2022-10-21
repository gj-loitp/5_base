package vn.loitp.app.activity.game.pong.pong

class OnePlayerGame(
    paddleA: PaddleAbstract,
    paddleB: PaddleAbstract,
    private val ball: Ball,
    difficulty: Difficulty,
    var bestScore: Int
) : GameAbstract(paddleA, paddleB, ball, difficulty) {

    var points = 0

    override fun bounce() {
        if (checkBounce()) {
            points++
        }
    }

    override fun referee(width: Int): Boolean {
        return when {
            ball.ballX < 0f ||
                    ball.ballX + ball.size > width -> {
                if (points > bestScore) {
                    bestScore = points
                }
                points = 0
                true
            }
            else -> false
        }
    }
}

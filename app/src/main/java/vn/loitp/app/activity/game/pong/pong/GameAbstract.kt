package vn.loitp.app.activity.game.pong.pong

import kotlin.random.Random

abstract class GameAbstract(
    private val paddleA: PaddleAbstract,
    private val paddleB: PaddleAbstract,
    private val ball: Ball,
    private val difficulty: Difficulty
) {
    abstract fun referee(width: Int): Boolean
    abstract fun bounce()

    fun checkBounce(): Boolean {
        val left = paddleA.paddleX + paddleA.width
        val right = paddleB.paddleX - paddleB.width

        return if (((ball.ballX in (left..(left - ball.dx))) && (ball.ballY + ball.size / 2 in (paddleA.paddleY..paddleA.paddleY + paddleA.height))) ||
            (ball.ballX + ball.size in (right - ball.dx..right)) && (ball.ballY + ball.size / 2 in (paddleB.paddleY..paddleB.paddleY + paddleA.height))
        ) {
            ball.playPaddleBounceSound()
            ball.flipDirection(Ball.SpeedComponent.X)
            if (difficulty == Difficulty.HARD) {
                if (Random.nextFloat() > 0.5) {
                    ball.flipDirection(Ball.SpeedComponent.Y)
                }
            }
            true
        } else {
            false
        }
        TODO("better collisions")
    }
}
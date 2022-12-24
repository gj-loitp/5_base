package vn.loitp.a.game.pong.pong

import android.view.MotionEvent

class SmoothPaddle(
    side: Side,
    x: Float,
    y: Float,
    difficulty: Difficulty
) :
    PaddleAbstract(side, x, y, difficulty) {
    private var movementState: MovementState = MovementState.MOVING_NOT
    private val speed = 30f

    override fun movePaddle(
        event: MotionEvent,
        index: Int,
        height: Int
    ) {
        movementState = when {
            event.getY(index) > height / 2f -> {
                MovementState.MOVING_UP
            }
            else -> {
                MovementState.MOVING_DOWN
            }
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_UP -> {
                movementState = MovementState.MOVING_NOT
            }
        }
    }

    fun update(height: Int) {
        when (movementState) {
            MovementState.MOVING_DOWN -> {
                if (paddleY >= 0)
                    paddleY -= speed
            }
            MovementState.MOVING_UP -> {
                if (paddleY <= height - this.height)
                    paddleY += speed
            }
            MovementState.MOVING_NOT -> {
            }
        }
    }

    enum class MovementState {
        MOVING_UP,
        MOVING_DOWN,
        MOVING_NOT
    }
}

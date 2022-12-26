package vn.loitp.a.game.pong.pong

import android.view.MotionEvent

class SimplePaddle(
    side: Side,
    x: Float,
    y: Float,
    difficulty: Difficulty
) :
    PaddleAbstract(side, x, y, difficulty) {

    override fun movePaddle(
        event: MotionEvent,
        index: Int,
        height: Int
    ) {
        updatePosition(event.getY(index) - this.height / 2)
    }
}

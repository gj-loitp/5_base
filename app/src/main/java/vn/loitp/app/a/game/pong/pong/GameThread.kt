package vn.loitp.app.a.game.pong.pong

import android.view.SurfaceHolder

class GameThread(
    private val surfaceHolder: SurfaceHolder,
    private val gameView: GameView
) : Thread() {

    private val targetFPS = 60
    var running: Boolean = false

    override fun run() {
        var startTime: Long
        val targetTime = (1000 / targetFPS).toLong()

        while (running) {
            startTime = System.nanoTime()

            surfaceHolder.lockCanvas()?.also {
                synchronized(surfaceHolder) {
                    gameView.update()
                    gameView.draw(it)
                }
                surfaceHolder.unlockCanvasAndPost(it)
            }

            try {
                sleep(targetTime - (System.nanoTime() - startTime) / 1000000)
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }
    }
}

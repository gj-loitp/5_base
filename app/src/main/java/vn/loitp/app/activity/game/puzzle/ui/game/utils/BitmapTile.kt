package vn.loitp.app.activity.game.puzzle.ui.game.utils

import android.graphics.Bitmap
import android.util.Size

data class BitmapTile(
    val image: Bitmap,
    val size: Size
) {
    private val tileSize = Size(
        image.width / size.width,
        image.height / size.height
    )

    val tiles = Array(size.height) { y ->
        Array(size.width) { x ->
            Bitmap.createBitmap(
                image,
                x * tileSize.width,
                y * tileSize.height,
                tileSize.width, tileSize.height
            )
        }
    }
}
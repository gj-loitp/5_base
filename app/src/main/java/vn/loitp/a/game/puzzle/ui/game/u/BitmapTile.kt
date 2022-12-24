package vn.loitp.a.game.puzzle.ui.game.u

import android.graphics.Bitmap
import android.util.Size

data class BitmapTile(
    val image: Bitmap,
    val size: Size
) {
    private val tileSize = Size(
        /* width = */ image.width / size.width,
        /* height = */ image.height / size.height
    )

    val tiles = Array(size.height) { y ->
        Array(size.width) { x ->
            Bitmap.createBitmap(
                /* source = */ image,
                /* x = */ x * tileSize.width,
                /* y = */ y * tileSize.height,
                /* width = */ tileSize.width, /* height = */ tileSize.height
            )
        }
    }
}

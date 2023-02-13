package vn.loitp.up.a.cv.rv.fitGv

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.views.rv.fitGridView.FitGridAdapter
import vn.loitp.R

internal class Adapter(
    context: Context,
    var onClick: ((Int) -> Unit)? = null
) : FitGridAdapter(
    context = context,
    itemId = R.layout.view_item_grid
) {

    private val drawables = intArrayOf(
        R.drawable.face_1,
        R.drawable.face_2,
        R.drawable.face_3,
        R.drawable.face_4,
        R.drawable.face_5,
        R.drawable.face_6,
        R.drawable.face_7,
        R.drawable.face_8,
        R.drawable.face_9,
        R.drawable.face_10,
        R.drawable.face_11,
        R.drawable.face_12
    )

    override fun onBindView(position: Int, view: View) {
        val ivGridItem = view.findViewById<AppCompatImageView>(R.id.ivGridItem)
        ivGridItem.setImageResource(drawables[position])
        ivGridItem.setSafeOnClickListener {
            onClick?.invoke(position)
        }
    }
}

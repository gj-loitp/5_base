package vn.loitp.app.a.cv.recyclerview.fitGridView

import android.content.Context
import android.view.View
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.views.rv.fitGridView.FitGridAdapter
import kotlinx.android.synthetic.main.view_item_grid.view.*
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
        view.ivGridItem?.setImageResource(drawables[position])

        view.ivGridItem?.setSafeOnClickListener {
            onClick?.invoke(position)
        }
    }
}

package vn.loitp.app.activity.customviews.recyclerview.fitgridview

import android.content.Context
import android.view.View
import com.views.recyclerview.fitgridview.FitGridAdapter
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.row_grid_item.view.*
import vn.loitp.app.R

internal class Adapter(
        context: Context,
        var onClick: ((Int) -> Unit)? = null
) : FitGridAdapter(
        context = context,
        itemId = R.layout.row_grid_item) {


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
            R.drawable.face_12)

    override fun onBindView(position: Int, view: View) {
        view.ivGridItem?.setImageResource(drawables[position])

        view.ivGridItem?.setSafeOnClickListener {
            onClick?.invoke(position)
        }
    }
}
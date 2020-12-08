package vn.loitp.app.activity.customviews.recyclerview.fitgridview

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.recyclerview.fitgridview.FitGridView
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_fit_grid_view.*
import vn.loitp.app.R

@LogTag("FitGridViewActivity")
@IsFullScreen(false)
class FitGridViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_fit_grid_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        gridView.setFitGridAdapter(
                Adapter(this) { pos ->
                    showShortInformation("Click $pos")
                }
        )
        btShowInDialog.setSafeOnClickListener {
            showAlert()
        }
        btChangeSize.setSafeOnClickListener {
            changeSize()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setNegativeButton(android.R.string.no) { _, _ ->

        }
        val gridView = FitGridView(this)
        gridView.numColumns = 3
        gridView.numRows = 4
        gridView.setFitGridAdapter(
                Adapter(this) { pos ->
                    showShortInformation("Click $pos")
                }
        )
        builder.setView(gridView)
        builder.show()
    }

    private var counter = 0
    private fun changeSize() {
        when (counter) {
            0 -> {
                changeSize(row = 2, column = 2)
            }
            1 -> {
                changeSize(row = 3, column = 3)
            }
            2 -> {
                changeSize(row = 4, column = 3)
            }
        }
        counter = ++counter % 3
    }

    private fun changeSize(row: Int, column: Int) {
        gridView.numRows = row
        gridView.numColumns = column
        gridView.update()
    }
}

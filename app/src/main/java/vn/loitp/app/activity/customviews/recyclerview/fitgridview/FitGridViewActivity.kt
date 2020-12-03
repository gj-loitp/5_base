package vn.loitp.app.activity.customviews.recyclerview.fitgridview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
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
        gridView.setFitGridAdapter(Adapter(this))
        btShowInDialog.setSafeOnClickListener {
            showAlert()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setNegativeButton(android.R.string.no) { _, _ ->

        }
        val gridView = FitGridView(this)
        gridView.numColumns = 3
        gridView.numRows = 4
        gridView.setFitGridAdapter(Adapter(this))
        builder.setView(gridView)
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fit_grid_view, menu)
        return true
    }

    private var counter = 0
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItem) {
            when (counter) {
                0 -> {
                    item.title = "2*2"
                    changeSize(row = 2, column = 2)
                }
                1 -> {
                    item.title = "3*3"
                    changeSize(row = 3, column = 3)
                }
                2 -> {
                    item.title = "4*3"
                    changeSize(row = 4, column = 3)
                }
            }
        }
        counter = ++counter % 3
        return super.onOptionsItemSelected(item)
    }

    private fun changeSize(row: Int, column: Int) {
        gridView.numRows = row
        gridView.numColumns = column
        gridView.update()
    }
}

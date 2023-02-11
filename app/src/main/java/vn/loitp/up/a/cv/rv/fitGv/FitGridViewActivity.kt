package vn.loitp.up.a.cv.rv.fitGv

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.rv.fitGridView.FitGridView
import vn.loitp.R
import vn.loitp.databinding.AFitGridViewBinding

@LogTag("FitGridViewActivity")
@IsFullScreen(false)
class FitGridViewActivity : BaseActivityFont() {
    private lateinit var binding: AFitGridViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFitGridViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FitGridViewActivity::class.java.simpleName
        }
        binding.gridView.setFitGridAdapter(
            Adapter(this) { pos ->
                showShortInformation("Click $pos")
            }
        )
        binding.btShowInDialog.setSafeOnClickListener {
            showAlert()
        }
        binding.btChangeSize.setSafeOnClickListener {
            changeSize()
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
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

    private fun changeSize(
        row: Int,
        column: Int
    ) {
        binding.gridView.numRows = row
        binding.gridView.numColumns = column
        binding.gridView.update()
    }
}

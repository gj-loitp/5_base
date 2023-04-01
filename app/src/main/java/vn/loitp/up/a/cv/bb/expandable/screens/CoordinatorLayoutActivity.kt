package vn.loitp.up.a.cv.bb.expandable.screens

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.AEbbCoordinatorLayoutBinding

@LogTag("CoordinatorLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CoordinatorLayoutActivity : BaseActivityFont() {
    private lateinit var binding: AEbbCoordinatorLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEbbCoordinatorLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Meow", Snackbar.LENGTH_LONG).show()
        }
    }
}

package vn.loitp.up.a.game.pong.a

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.hideDefaultControls
import vn.loitp.R
import vn.loitp.databinding.APongSettingsBinding
import vn.loitp.up.a.game.pong.pong.Difficulty
import vn.loitp.up.a.game.pong.pong.Mode
import vn.loitp.up.a.game.pong.pong.Settings
import java.util.*

@LogTag("SettingsActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SettingsActivity : BaseActivityFont() {
    private lateinit var binding: APongSettingsBinding

    private val settings = Settings()

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APongSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        LScreenUtil.setScreenOrientation(this, false)
        this.hideDefaultControls()
//        setCustomStatusBar(Color.BLACK, Color.BLACK)

        ArrayAdapter.createFromResource(this, R.array.modeTypes, R.layout.custom_spinner)
            .also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.custom_row)
                // Apply the adapter to the spinner
                binding.pvpSpinner.adapter = adapter
            }

        ArrayAdapter.createFromResource(this, R.array.difficulty_types, R.layout.custom_spinner)
            .also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(R.layout.custom_row)
                // Apply the adapter to the spinner
                binding.difficultySpinner.adapter = adapter
            }

        binding.pvpSpinner.onItemSelectedListener = this.ModeSpinnerHandler()
        binding.difficultySpinner.onItemSelectedListener = this.DifficultySpinnerHandler()
    }

    @Suppress("unused")
    fun play(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("settings", settings)
        startActivity(intent)
    }

    private inner class ModeSpinnerHandler : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(
            parent: AdapterView<*>, view: View, position: Int, id: Long
        ) {
            settings.pvp = Mode.valueOf(
                parent.getItemAtPosition(position).toString().uppercase(Locale.ROOT).replace(
                    ' ', '_'
                )
            )
        }
    }

    private inner class DifficultySpinnerHandler : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(
            parent: AdapterView<*>, view: View, position: Int, id: Long
        ) {
            settings.difficulty = Difficulty.valueOf(
                parent.getItemAtPosition(position).toString().uppercase(Locale.ROOT)
            )
        }
    }
}

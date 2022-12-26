package vn.loitp.a.game.puzzle.ui.options

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProviders
import vn.loitp.R

typealias BoardSizeAdapter = ArrayAdapter<BoardTitledSize>

class BoardSizeSpinnerFragment : androidx.fragment.app.Fragment() {
    private val viewModel: BoardOptionsViewModel? by lazy {
        activity?.let {
            ViewModelProviders.of(it)[BoardOptionsViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.f_puzzel_board_size_spinner, container, false)
        val sizeSpinner = view.findViewById<Spinner>(R.id.board_size_spinner)
        val arrayAdapter = BoardSizeAdapter(
            activity as Context,
            R.layout.v_game_toolbar_spinner_item,
            BoardOptionsViewModel.PREDEFINED_BOARD_SIZE
        )

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sizeSpinner.adapter = arrayAdapter

        sizeSpinner.setSelection(arrayAdapter.getPosition(viewModel?.boardSize?.value))
        sizeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position)
                selectedItem.let {
                    if (viewModel?.boardSize?.value?.equals(selectedItem) == true)
                        return

                    viewModel?.boardSize?.value = selectedItem as BoardTitledSize
                }
            }
        }

        return view
    }
}

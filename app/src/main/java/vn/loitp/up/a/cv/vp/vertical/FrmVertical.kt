package vn.loitp.up.a.cv.vp.vertical

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.databinding.FVpVerticalBinding

class FrmVertical : Fragment() {

    companion object {

        private const val TEXT = "text"

        fun newInstance(data: String): FrmVertical {
            val fragment = FrmVertical()
            val bundle = Bundle(1)
            bundle.putString(TEXT, data)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FVpVerticalBinding

    var text: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        text = arguments?.getString(TEXT) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FVpVerticalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.tv.text = text
    }
}

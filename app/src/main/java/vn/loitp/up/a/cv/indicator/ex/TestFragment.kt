package vn.loitp.up.a.cv.indicator.ex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.loitp.databinding.FTestMagicIndicatorBinding

class TestFragment : Fragment() {
    companion object {
        const val EXTRA_TEXT = "extra_text"
    }

    private lateinit var binding: FTestMagicIndicatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FTestMagicIndicatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            binding.textView.text = it.getString(EXTRA_TEXT)
        }
    }
}
